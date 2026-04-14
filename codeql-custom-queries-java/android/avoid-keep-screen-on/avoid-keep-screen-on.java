class WindowManager {
    static class LayoutParams {
        static final int FLAG_KEEP_SCREEN_ON = 128;
        static final int FLAG_NOT_KEEP_SCREEN_ON = 64;
    }
}

// ============================================================
// 🚫 Noncompliant - direct use of FLAG_KEEP_SCREEN_ON
// ============================================================
class NoncompliantDirectFlagUsage {

    void keepScreenOnFlag() {
        int flag = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - FLAG_KEEP_SCREEN_ON in bitwise operation
// ============================================================
class NoncompliantBitwiseFlagUsage {

    void applyFlag() {
        int flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | 0x40; // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - storing FLAG_KEEP_SCREEN_ON in a variable
// ============================================================
class NoncompliantStoredFlagUsage {

    void storeFlag() {
        int stored = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - FLAG_KEEP_SCREEN_ON in conditional
// ============================================================
class NoncompliantConditionalFlagUsage {

    void checkFlag(int input) {
        if (input == WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) { // $ Alert
            // Do something
        }
    }
}

// ============================================================
// 🚫 Noncompliant - FLAG_KEEP_SCREEN_ON in method parameter
// ============================================================
class NoncompliantParameterFlagUsage {

    void setFlag(int flag) {
        if (flag == WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) { // $ Alert
            // Handle flag
        }
    }
}

// ============================================================
// 🚫 Noncompliant - FLAG_KEEP_SCREEN_ON in loop
// ============================================================
class NoncompliantLoopFlagUsage {

    void loopWithFlag() {
        for (int i = 0; i < 10; i++) {
            int flag = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // $ Alert
        }
    }
}

// ============================================================
// ✅ Compliant - no FLAG_KEEP_SCREEN_ON usage
// ============================================================
class CompliantNoFlagUsage {

    void doNothing() {
        int x = 42; // OK
    }
}

// ============================================================
// ✅ Compliant - using FLAG_NOT_KEEP_SCREEN_ON
// ============================================================
class CompliantAlternativeFlagUsage {

    void useAlternativeFlag() {
        int flag = WindowManager.LayoutParams.FLAG_NOT_KEEP_SCREEN_ON; // OK
    }
}

// ============================================================
// ✅ Compliant - operation with another FLAG_KEEP_SCREEN_ON
// ============================================================
class OtherForTest {
    public static final int FLAG_KEEP_SCREEN_ON = 10;
}

class CompliantBitwiseWithoutFlag {

    void applyAlternativeFlag() {
        int flags = OtherForTest.FLAG_KEEP_SCREEN_ON; //OK
    }
}

// ============================================================
// ✅ Compliant - conditional without FLAG_KEEP_SCREEN_ON
// ============================================================
class CompliantConditionalWithoutFlag {

    void checkAlternativeFlag(int input) {
        if (input == WindowManager.LayoutParams.FLAG_NOT_KEEP_SCREEN_ON) { // OK
            // Do something
        }
    }
}