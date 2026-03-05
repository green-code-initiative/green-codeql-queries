class WindowManager {
    static class LayoutParams {
        static final int FLAG_KEEP_SCREEN_ON = 128;
    }
}

// 🚫 Noncompliant - using FLAG_KEEP_SCREEN_ON
class NoncompliantKeepScreenOn {

    void keepScreenOnFlag() {
        int flag = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // $ Alert
    }

    void applyFlag() {
        int flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | 0x40; // $ Alert
    }

    void storeFlag() {
        int stored = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // $ Alert
    }
}

// ✅ Compliant - no keep screen on usage
class CompliantKeepScreenOn {

    void doNothing() {
        int x = 42;
    }
}