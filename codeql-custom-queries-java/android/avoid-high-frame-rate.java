class Surface {
    void setFrameRate(float frameRate, int compatibility) {}
}

class SurfaceCompatibility {
    static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
}

// ============================================================
// 🚫 Noncompliant - frameRate set to 90f
// ============================================================
class NoncompliantFrameRate90 {

    void set90Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(90f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - frameRate set to 120f
// ============================================================
class NoncompliantFrameRate120 {

    void set120Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(120f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - frameRate set to 144f
// ============================================================
class NoncompliantFrameRate144 {

    void set144Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(144f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - frameRate set to 60.1f (just above limit)
// ============================================================
class NoncompliantFrameRateJustAbove {

    void setJustAbove60Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(60.1f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - high frame rate inside a condition
// ============================================================
class NoncompliantFrameRateInCondition {

    void setHighFrameRateIfSupported(boolean supported) {
        Surface surface = new Surface();
        if (supported) {
            surface.setFrameRate(120f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - high frame rate inside a loop
// ============================================================
class NoncompliantFrameRateInLoop {

    void setHighFrameRateInLoop() {
        Surface surface = new Surface();
        for (int i = 0; i < 3; i++) {
            surface.setFrameRate(90f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - multiple high frame rate calls
// ============================================================
class NoncompliantMultipleHighFrameRates {

    void setHighFrameRateFirst() {
        Surface surface = new Surface();
        surface.setFrameRate(90f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }

    void setHighFrameRateSecond() {
        Surface surface = new Surface();
        surface.setFrameRate(120f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - frameRate set to 60f (limit)
// ============================================================
class CompliantFrameRate60 {

    void set60Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(60f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // OK
    }
}

// ============================================================
// ✅ Compliant - frameRate set to 30f
// ============================================================
class CompliantFrameRate30 {

    void set30Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(30f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // OK
    }
}

// ============================================================
// ✅ Compliant - frameRate set to 24f (cinema standard)
// ============================================================
class CompliantFrameRate24 {

    void set24Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(24f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE); // OK
    }
}

// ============================================================
// ✅ Compliant - no setFrameRate call at all
// ============================================================
class CompliantNoFrameRate {

    void doSomethingElse() {
        int x = 42;
        String msg = "no frame rate here";
    }
}