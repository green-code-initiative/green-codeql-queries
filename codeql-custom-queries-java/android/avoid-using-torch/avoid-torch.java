class CameraManager {
    void setTorchMode(String cameraId, boolean enabled) {}
}

// ============================================================
// 🚫 Noncompliant - setTorchMode with true
// ============================================================
class NoncompliantTorchEnabled {

    void enableTorch() {
        CameraManager manager = new CameraManager();
        manager.setTorchMode("0", true); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - setTorchMode true in a condition block
// ============================================================
class NoncompliantTorchInCondition {

    void enableTorchIfNeeded(boolean needed) {
        CameraManager manager = new CameraManager();
        if (needed) {
            manager.setTorchMode("0", true); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - setTorchMode true inside a loop
// ============================================================
class NoncompliantTorchInLoop {

    void enableTorchInLoop() {
        CameraManager manager = new CameraManager();
        for (int i = 0; i < 3; i++) {
            manager.setTorchMode("0", true); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - multiple torch enable calls in same class
// ============================================================
class NoncompliantMultipleTorchCalls {

    void enableTorchFront() {
        CameraManager manager = new CameraManager();
        manager.setTorchMode("0", true); // $ Alert
    }

    void enableTorchBack() {
        CameraManager manager = new CameraManager();
        manager.setTorchMode("1", true); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - setTorchMode with false (disabling torch)
// ============================================================
class CompliantTorchDisabled {

    void disableTorch() {
        CameraManager manager = new CameraManager();
        manager.setTorchMode("0", false); // OK
    }
}

// ============================================================
// ✅ Compliant - setTorchMode true then immediately false
// ============================================================
class CompliantTorchEnabledThenDisabled {

    void flashOnce() {
        CameraManager manager = new CameraManager();
        manager.setTorchMode("0", false); // OK
    }
}

// ============================================================
// ✅ Compliant - no setTorchMode call at all
// ============================================================
class CompliantNoTorch {

    void doSomethingElse() {
        int x = 42;
        String msg = "no torch here";
    }
}