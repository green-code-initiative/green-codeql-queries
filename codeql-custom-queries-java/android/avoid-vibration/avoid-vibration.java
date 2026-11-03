class VibratorContext {
    static final String VIBRATOR_SERVICE         = "vibrator";
    static final String VIBRATOR_MANAGER_SERVICE = "vibrator_manager";

    Object getSystemService(String name) { return null; }
}

// ============================================================
// 🚫 Noncompliant - getSystemService with VIBRATOR_SERVICE
// ============================================================
class NoncompliantVibratorService {

    void triggerVibration() {
        VibratorContext ctx = new VibratorContext();
        ctx.getSystemService(VibratorContext.VIBRATOR_SERVICE); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - getSystemService with VIBRATOR_MANAGER_SERVICE
// ============================================================
class NoncompliantVibratorManagerService {

    void triggerVibrationManager() {
        VibratorContext ctx = new VibratorContext();
        ctx.getSystemService(VibratorContext.VIBRATOR_MANAGER_SERVICE); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - both VIBRATOR_SERVICE calls in same class
// ============================================================
class NoncompliantMultipleVibrations {

    void vibrateOnClick() {
        VibratorContext ctx = new VibratorContext();
        ctx.getSystemService(VibratorContext.VIBRATOR_SERVICE); // $ Alert
    }

    void vibrateOnLongClick() {
        VibratorContext ctx = new VibratorContext();
        ctx.getSystemService(VibratorContext.VIBRATOR_SERVICE); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - VIBRATOR_SERVICE inside a loop
// ============================================================
class NoncompliantVibrationInLoop {

    void vibrateInLoop() {
        VibratorContext ctx = new VibratorContext();
        for (int i = 0; i < 3; i++) {
            ctx.getSystemService(VibratorContext.VIBRATOR_SERVICE); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - both services used in same method
// ============================================================
class NoncompliantBothServices {

    void useBothVibratorServices() {
        VibratorContext ctx = new VibratorContext();
        ctx.getSystemService(VibratorContext.VIBRATOR_SERVICE);         // $ Alert
        ctx.getSystemService(VibratorContext.VIBRATOR_MANAGER_SERVICE); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - getSystemService with unrelated service
// ============================================================
class CompliantOtherService {

    static final String AUDIO_SERVICE = "audio";

    void useAudioService() {
        VibratorContext ctx = new VibratorContext();
        ctx.getSystemService(CompliantOtherService.AUDIO_SERVICE); // OK
    }
}

// ============================================================
// ✅ Compliant - no getSystemService call at all
// ============================================================
class CompliantNoVibration {

    void doSomethingElse() {
        int x = 42;
        String msg = "no vibration here";
    }
}