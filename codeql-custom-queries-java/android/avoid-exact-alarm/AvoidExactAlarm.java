// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}

class Activity extends Context {}

class PendingIntent {
    public static PendingIntent getBroadcast(Context c, int r, Object i, int f) { return null; }
}

class AlarmManager {
    public static final int RTC_WAKEUP = 0;
    public static final int ELAPSED_REALTIME_WAKEUP = 2;
    public static final int RTC = 1;
    public static final int ELAPSED_REALTIME = 3;

    public void setRepeating(int type, long triggerAtMillis, long intervalMillis, PendingIntent operation) {}
    public void setInexactRepeating(int type, long triggerAtMillis, long intervalMillis, PendingIntent operation) {}
    public void setExact(int type, long triggerAtMillis, PendingIntent operation) {}
    public void setExactAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation) {}
    public void set(int type, long triggerAtMillis, PendingIntent operation) {}
    public void setAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation) {}
    public void cancel(PendingIntent operation) {}
}

// NON-COMPLIANT — uses setRepeating instead of setInexactRepeating
class BadRepeatingAlarm extends Activity {
    void scheduleAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 60000, pi); // BAD
    }
}

// NON-COMPLIANT — uses setExact
class BadExactAlarm extends Activity {
    void scheduleAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pi); // BAD
    }
}

// NON-COMPLIANT — uses setExactAndAllowWhileIdle
class BadExactIdleAlarm extends Activity {
    void scheduleAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pi); // BAD
    }
}

// NON-COMPLIANT — calls setRepeating on a parameter reference
class BadExternalRepeating {
    void schedule(AlarmManager am) {
        PendingIntent pi = PendingIntent.getBroadcast(null, 0, null, 0);
        am.setRepeating(AlarmManager.RTC, 0, 30000, pi); // BAD
    }
}

// NON-COMPLIANT — calls setExact on a parameter reference
class BadExternalExact {
    void schedule(AlarmManager am) {
        PendingIntent pi = PendingIntent.getBroadcast(null, 0, null, 0);
        am.setExact(AlarmManager.ELAPSED_REALTIME, 1000, pi); // BAD
    }
}

// COMPLIANT — uses setInexactRepeating
class GoodInexactAlarm extends Activity {
    void scheduleAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, 60000, pi); // GOOD
    }
}

// COMPLIANT — uses set (inexact one-shot)
class GoodSetAlarm extends Activity {
    void scheduleAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pi); // GOOD
    }
}

// COMPLIANT — uses setAndAllowWhileIdle (inexact)
class GoodSetIdleAlarm extends Activity {
    void scheduleAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pi); // GOOD
    }
}

// COMPLIANT — only calls cancel
class GoodCancelAlarm extends Activity {
    void cancelAlarm() {
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, null, 0);
        am.cancel(pi); // GOOD
    }
}

// COMPLIANT — unrelated class with same method names
class SomeScheduler {
    public void setRepeating(int type, long trigger, long interval, Object op) {}
    public void setExact(int type, long trigger, Object op) {}
    public void setExactAndAllowWhileIdle(int type, long trigger, Object op) {}
}

class UnrelatedCaller {
    void doSomething() {
        SomeScheduler s = new SomeScheduler();
        s.setRepeating(0, 0, 60000, null);          // GOOD — not AlarmManager
        s.setExact(0, 0, null);                       // GOOD — not AlarmManager
        s.setExactAndAllowWhileIdle(0, 0, null);     // GOOD — not AlarmManager
    }
}

public class AvoidExactAlarm {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
