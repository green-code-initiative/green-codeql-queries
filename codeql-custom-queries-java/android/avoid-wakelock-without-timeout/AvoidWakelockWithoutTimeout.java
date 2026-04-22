// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}

class Activity extends Context {}

class PowerManager {
    public WakeLock newWakeLock(int levelAndFlags, String tag) { return null; }

    public static final int PARTIAL_WAKE_LOCK = 1;
    public static final int FULL_WAKE_LOCK = 26;

    class WakeLock {
        public void acquire() {}
        public void acquire(long timeout) {}
        public void release() {}
        public boolean isHeld() { return false; }
    }
}

// NON-COMPLIANT — calls acquire() without timeout
class BadWakeLockNoTimeout extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wl.acquire(); // BAD
        // ... do work ...
        wl.release();
    }
}

// NON-COMPLIANT — calls acquire() without timeout, no release at all
class BadWakeLockNoRelease extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wl.acquire(); // BAD
    }
}

// NON-COMPLIANT — calls acquire() without timeout on a method parameter
class BadExternalWakeLock {
    void start(PowerManager.WakeLock wl) {
        wl.acquire(); // BAD
    }
}

// NON-COMPLIANT — chained call from newWakeLock, no timeout
class BadChainedAcquire extends Activity {
    PowerManager.WakeLock wakeLock;

    void init() {
        PowerManager pm = (PowerManager) getSystemService("power");
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Screen");
        wakeLock.acquire(); // BAD
    }

    void cleanup() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}

// COMPLIANT — calls acquire(long timeout)
class GoodWakeLockWithTimeout extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wl.acquire(10 * 60 * 1000L); // GOOD — 10-minute timeout
        // ... do work ...
        wl.release();
    }
}

// COMPLIANT — calls acquire(long timeout) on a method parameter
class GoodExternalWakeLock {
    void start(PowerManager.WakeLock wl) {
        wl.acquire(5000L); // GOOD — 5-second timeout
    }
}

// COMPLIANT — calls acquire(long timeout) with a variable
class GoodWakeLockVariableTimeout extends Activity {
    private static final long TIMEOUT_MS = 30000L;

    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wl.acquire(TIMEOUT_MS); // GOOD — timeout via variable
        // ... do work ...
        wl.release();
    }
}

// COMPLIANT — never calls acquire at all
class GoodNoAcquire extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wl.release(); // GOOD — no acquire
    }
}

// COMPLIANT — unrelated class with a method named acquire
class SomeLock {
    public void acquire() {}
    public void acquire(long timeout) {}
    public void release() {}
}

class UnrelatedCaller {
    void doSomething() {
        SomeLock lock = new SomeLock();
        lock.acquire(); // GOOD — not PowerManager.WakeLock
    }
}

public class AvoidWakelockWithoutTimeout {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
