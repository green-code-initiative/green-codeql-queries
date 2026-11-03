class Application {
    void onCreate() {}
}

class AnalyticsLibrary {
    static void init(Object context) {}
    static void trackEvent(String event) {}
}

class CrashReporter {
    static void init(Object context) {}
    static void reportCrash(Throwable t) {}
}

class ImageLoader {
    static void init(Object context) {}
    static Object load(String url) { return null; }
}

class DatabaseHelper {
    static void init(Object context) {}
    static Object query(String sql) { return null; }
}

class PushNotificationService {
    static void init(Object context) {}
    static void sendPush(String message) {}
}

// ============================================================
// 🚫 Noncompliant - multiple library inits in Application#onCreate
// ============================================================
class NoncompliantExtraneousInit extends Application {

    void onCreate() {
        AnalyticsLibrary.init(null);      // $ Alert
        CrashReporter.init(null);         // $ Alert
        ImageLoader.init(null);           // $ Alert
        DatabaseHelper.init(null);        // $ Alert
        PushNotificationService.init(null); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - single library init in Application#onCreate
// ============================================================
class NoncompliantSingleInit extends Application {

    void onCreate() {
        AnalyticsLibrary.init(null); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - init inside condition in Application#onCreate
// ============================================================
class NoncompliantConditionalInit extends Application {

    void onCreate() {
        boolean debug = true;
        if (debug) {
            CrashReporter.init(null); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - chained calls in Application#onCreate
// ============================================================
class NoncompliantChainedInit extends Application {

    void onCreate() {
        ImageLoader.init(null);    // $ Alert
        DatabaseHelper.init(null); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - init called outside Application#onCreate
//               (e.g. in an Activity or on-demand)
// ============================================================
class CompliantOnDemandInit {

    void onUserLogin() {
        AnalyticsLibrary.init(null); // OK - called on demand
    }

    void onCrashDetected(Throwable t) {
        CrashReporter.init(null);    // OK - called on demand
        CrashReporter.reportCrash(t);
    }
}

// ============================================================
// ✅ Compliant - Application#onCreate with no init calls
// ============================================================
class CompliantEmptyOnCreate extends Application {

    void onCreate() {
        // No initialisation — compliant
    }
}

// ============================================================
// ✅ Compliant - Application subclass but init in another method
// ============================================================
class CompliantInitInOtherMethod extends Application {

    void onCreate() {
        // Nothing here — compliant
    }

    void initLibraries() {
        AnalyticsLibrary.init(null); // OK - not in onCreate
        ImageLoader.init(null);      // OK - not in onCreate
    }
}