package android.app;

class Context {
    Object startService(Object service) { return null; }
    boolean stopService(Object service) { return false; }
}

class Service extends Context {
    void stopSelf() {}
    void stopSelf(int startId) {}
}

class Intent {}

// ============================================================
// 🚫 Noncompliant - startService() seul, aucun stop nulle part
// ============================================================
class NoncompliantNoStop {

    void launchOnly() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - plusieurs startService(), aucun stop
// ============================================================
class NoncompliantMultipleStarts {

    void multipleStarts() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // $ Alert
        ctx.startService(new Intent()); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - stopService() dans une autre méthode
// ============================================================
class NoncompliantStopInOtherMethod {

    void startOnly() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // $ Alert
    }

    void stopOnly() {
        Context ctx = new Context();
        ctx.stopService(new Intent());
    }
}

// ============================================================
// 🚫 Noncompliant - stopSelf() dans une autre méthode
// ============================================================
class NoncompliantStopSelfInOtherMethod extends Service {

    void startOnly() {
        startService(new Intent()); // $ Alert
    }

    void stopOnly() {
        stopSelf();
    }
}

// ============================================================
// 🚫 Noncompliant - startService() dans une boucle, aucun stop
// ============================================================
class NoncompliantInLoop {

    void loopedStart() {
        Context ctx = new Context();
        for (int i = 0; i < 3; i++) {
            ctx.startService(new Intent()); // $ Alert
        }
    }
}

// ============================================================
// ✅ Compliant - startService() + stopService() dans la même méthode
// ============================================================
class CompliantStopServiceSameMethod {

    void safeStart() {
        Context ctx = new Context();
        ctx.startService(new Intent()); // OK
        ctx.stopService(new Intent());
    }
}

// ============================================================
// 🚫 Compliant - plusieurs startService() + stopService() dans la même méthode
// ============================================================
class CompliantMultipleStartsWithStop {

    void safeMultipleStarts() {
        Context ctx = new Context();
        Context ctx2 = new Context();
        ctx.startService(new Intent()); // OK
        ctx2.startService(new Intent()); // Alert
        ctx.stopService(new Intent());
    }
}

// ============================================================
// ✅ Compliant - startService() + stopSelf() dans la même méthode
// ============================================================
class CompliantStopSelfSameMethod extends Service {

    void safeWithStopSelf() {
        startService(new Intent()); // OK
        stopSelf();
    }
}

// ============================================================
// ✅ Compliant - startService() + stopSelf(int) dans la même méthode
// ============================================================
class CompliantStopSelfWithId extends Service {

    void safeWithStopSelfId() {
        startService(new Intent()); // OK
        stopSelf(1);
    }
}

// ============================================================
// ✅ Compliant - stopService() seul, sans startService()
// ============================================================
class CompliantStopOnly {

    void stopOnly() {
        Context ctx = new Context();
        ctx.stopService(new Intent()); // OK
    }
}