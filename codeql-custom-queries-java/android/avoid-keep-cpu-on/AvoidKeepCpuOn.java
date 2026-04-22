// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}

class Activity extends Context {}

class PowerManager {
    public static final int PARTIAL_WAKE_LOCK = 1;
    public static final int SCREEN_DIM_WAKE_LOCK = 6;
    public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;
    public static final int FULL_WAKE_LOCK = 26;
    public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;

    public WakeLock newWakeLock(int levelAndFlags, String tag) { return null; }

    class WakeLock {
        public void acquire() {}
        public void acquire(long timeout) {}
        public void release() {}
        public boolean isHeld() { return false; }
    }
}

class Window {
    public Window() {}
    public void addFlags(int flags) {}
    public void clearFlags(int flags) {}
}

class WindowManager {
    static class LayoutParams {
        public static final int FLAG_KEEP_SCREEN_ON = 128;
    }
}

class View {
    public View(Context context) {}
    public void setKeepScreenOn(boolean keepScreenOn) {}
}

// NON-COMPLIANT — creates a SCREEN_DIM_WAKE_LOCK to keep screen on
class BadScreenDimWakeLock extends Activity {
    void keepScreen() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "ScreenDim"); // BAD
        wl.acquire();
    }
}

// NON-COMPLIANT — creates a SCREEN_BRIGHT_WAKE_LOCK
class BadScreenBrightWakeLock extends Activity {
    void keepScreen() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "ScreenBright"); // BAD
        wl.acquire(60000L);
    }
}

// NON-COMPLIANT — creates a FULL_WAKE_LOCK (screen + CPU)
class BadFullWakeLock extends Activity {
    void keepScreen() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Full"); // BAD
        wl.acquire();
    }
}

// NON-COMPLIANT — uses raw int value 26 (FULL_WAKE_LOCK)
class BadRawIntWakeLock extends Activity {
    void keepScreen() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(26, "RawFull"); // BAD
        wl.acquire();
    }
}

// NON-COMPLIANT — uses FLAG_KEEP_SCREEN_ON via Window.addFlags
class BadKeepScreenOnFlag extends Activity {
    void keepScreen() {
        Window w = new Window();
        w.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // BAD
    }
}

// NON-COMPLIANT — uses raw int 128 for FLAG_KEEP_SCREEN_ON via Window.addFlags
class BadKeepScreenOnRawInt extends Activity {
    void keepScreen() {
        Window w = new Window();
        w.addFlags(128); // BAD
    }
}

// NON-COMPLIANT — uses View.setKeepScreenOn(true)
class BadViewKeepScreenOn extends Activity {
    void keepScreen() {
        View v = new View(this);
        v.setKeepScreenOn(true); // BAD
    }
}

// NON-COMPLIANT — newWakeLock on a parameter reference
class BadExternalWakeLock {
    void create(PowerManager pm) {
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Ext"); // BAD
        wl.acquire(10000L);
    }
}

// COMPLIANT — uses PARTIAL_WAKE_LOCK (CPU only, screen off)
class GoodPartialWakeLock extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPU"); // GOOD
        wl.acquire(60000L);
        wl.release();
    }
}

// COMPLIANT — uses PROXIMITY_SCREEN_OFF_WAKE_LOCK (turns screen off on proximity)
class GoodProximityWakeLock extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "Prox"); // GOOD
        wl.acquire();
    }
}

// COMPLIANT — uses raw int value 1 (PARTIAL_WAKE_LOCK)
class GoodRawIntPartial extends Activity {
    void doWork() {
        PowerManager pm = (PowerManager) getSystemService("power");
        PowerManager.WakeLock wl = pm.newWakeLock(1, "Partial"); // GOOD
        wl.acquire(30000L);
    }
}

// COMPLIANT — View.setKeepScreenOn(false) — disabling keep screen on
class GoodViewDisableKeepScreenOn extends Activity {
    void disableScreen() {
        View v = new View(this);
        v.setKeepScreenOn(false); // GOOD — turning it off
    }
}

// COMPLIANT — Window.clearFlags with FLAG_KEEP_SCREEN_ON (removing the flag)
class GoodClearKeepScreenOn extends Activity {
    void disableScreen() {
        Window w = new Window();
        w.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // GOOD — clearing
    }
}

// COMPLIANT — no wake lock or screen-on usage at all
class NeutralActivity extends Activity {
    void doWork() {
        System.out.println("Hello");
    }
}

// COMPLIANT — unrelated class with same method names
class FakePowerManager {
    public Object newWakeLock(int flags, String tag) { return null; }
}

class UnrelatedNewWakeLock {
    void doSomething() {
        FakePowerManager fpm = new FakePowerManager();
        fpm.newWakeLock(26, "Fake"); // GOOD — not PowerManager
    }
}

// COMPLIANT — unrelated addFlags call
class FakeWindow {
    public void addFlags(int flags) {}
}

class UnrelatedAddFlags {
    void doSomething() {
        FakeWindow fw = new FakeWindow();
        fw.addFlags(128); // GOOD — not Window
    }
}

// COMPLIANT — unrelated setKeepScreenOn call
class FakeView {
    public void setKeepScreenOn(boolean keep) {}
}

class UnrelatedSetKeepScreenOn {
    void doSomething() {
        FakeView fv = new FakeView();
        fv.setKeepScreenOn(true); // GOOD — not View
    }
}

public class AvoidKeepCpuOn {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
