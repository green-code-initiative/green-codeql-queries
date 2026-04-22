package android.view;

interface WindowManager {

    static class LayoutParams {

        float screenBrightness;
        float buttonBrightness;

        static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;
        static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
        static final float BRIGHTNESS_OVERRIDE_OFF  = 0.0f;

        static final int FLAG_KEEP_SCREEN_ON = 0x00000080;
    }
}

// ============================================================
// 🚫 Noncompliant - screenBrightness = BRIGHTNESS_OVERRIDE_FULL
// ============================================================
class NoncompliantBrightnessOverride {

    void disableAdaptiveBrightness() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL; // $ Alert
    }

    void disableAdaptiveBrightnessWithVar() {
        float override = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.screenBrightness = override; // $ Alert
    }

    void disableInLoop() {
        for (int i = 0; i < 3; i++) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL; // $ Alert
        }
    }
}

// ============================================================
// ✅ Compliant - screenBrightness non forcée au maximum
// ============================================================
class CompliantBrightnessUsage {

    void useAdaptiveBrightness() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE; // OK
    }

    void useCustomBrightness() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.screenBrightness = 0.5f; // OK
    }

    void noScreenBrightnessSet() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        int x = 42; // OK
    }
}