class Context {
    Object startService(Object service) { return null; }
    boolean stopService(Object service) { return false; }
}

class Service extends Context {}

class Intent {}

// 🚫 Noncompliant - startService() without stopService() or stopSelf()
class NoncompliantEverlastingService {

    void leakingLaunch() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // $ Alert
    }

    void leakingLaunchMultiple() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // $ Alert
        ctx.startService(new Intent()); // $ Alert
    }

    void leakingNoStopInSameMethod() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // $ Alert
    }

    void stopElsewhere() {
        Context ctx = new Context();
        ctx.stopService(new Intent());
    }
}

// ✅ Compliant - startService() with stopService() in same method
class CompliantStopService {

    void safeWithStopService() {
        Context ctx = new Context();
        ctx.startService(new Intent());
        ctx.stopService(new Intent());
    }

    void safeMultiple() {
        Context ctx = new Context();
        ctx.startService(new Intent());
        ctx.startService(new Intent());
        ctx.stopService(new Intent());
    }
}

// ✅ Compliant - startService() with stopSelf() in same method
class CompliantStopSelf extends Service {

    void safeWithStopSelf() {
        startService(new Intent());
        stopSelf();
    }

    void stopSelf() {}
}