// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}
class Bundle {}
class Application extends Context {
    public void onCreate() {}
}
class Activity extends Context {
    protected void onCreate(Bundle b) {}
}
class Service extends Context {}
class BroadcastReceiver {}
class PendingIntent {}

class JobInfo {
    public static Builder newBuilder() { return null; }
    static class Builder {
        public Builder setPeriodic(long interval) { return this; }
        public JobInfo build() { return null; }
    }
}

class JobScheduler {
    public int schedule(JobInfo job) { return 0; }
}

class AlarmManager {
    public void set(int type, long triggerAtMillis, PendingIntent op) {}
    public void setRepeating(int type, long triggerAtMillis, long interval, PendingIntent op) {}
    public void setExact(int type, long triggerAtMillis, PendingIntent op) {}
    public void setInexactRepeating(int type, long triggerAtMillis, long interval, PendingIntent op) {}
}

// NON-COMPLIANT — uses AlarmManager without JobScheduler
class BadActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        am.setRepeating(0, 0L, 60000L, null);
    }
}

// COMPLIANT — uses JobScheduler
class GoodActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        JobScheduler js = (JobScheduler) getSystemService("jobscheduler");
        JobInfo job = JobInfo.newBuilder().setPeriodic(60000L).build();
        js.schedule(job);
    }
}

// COMPLIANT — uses both but has JobScheduler
class MixedActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        am.set(0, 0L, null);
        JobScheduler js = (JobScheduler) getSystemService("jobscheduler");
        js.schedule(null);
    }
}

public class MissingJobSchedulerUsage {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
