// WindowManager et WindowManager.LayoutParams sont déjà déclarés dans avoid-keep-screen-on.java
// avec seulement FLAG_KEEP_SCREEN_ON — on ne peut pas les redéclarer ni les modifier.
// On utilise une classe proxy BrightnessParams qui simule WindowManager.LayoutParams
// pour les champs screenBrightness et BRIGHTNESS_OVERRIDE_FULL.

class BrightnessParams {
    float screenBrightness;
    static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;
    static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
}

// 🚫 Noncompliant - screenBrightness set to BRIGHTNESS_OVERRIDE_FULL
class NoncompliantBrightnessOverride {

    void disableAdaptiveBrightness() {
        BrightnessParams params = new BrightnessParams();
        params.screenBrightness = BrightnessParams.BRIGHTNESS_OVERRIDE_FULL; // $ Alert
    }

    void disableAdaptiveBrightnessWithVar() {
        float override = BrightnessParams.BRIGHTNESS_OVERRIDE_FULL;
        BrightnessParams params = new BrightnessParams();
        params.screenBrightness = override; // $ Alert
    }

    void disableInLoop() {
        for (int i = 0; i < 3; i++) {
            BrightnessParams params = new BrightnessParams();
            params.screenBrightness = BrightnessParams.BRIGHTNESS_OVERRIDE_FULL; // $ Alert
        }
    }
}

// ✅ Compliant - screenBrightness not set to BRIGHTNESS_OVERRIDE_FULL
class CompliantBrightnessUsage {

    void useAdaptiveBrightness() {
        BrightnessParams params = new BrightnessParams();
        params.screenBrightness = BrightnessParams.BRIGHTNESS_OVERRIDE_NONE;
    }

    void useCustomBrightness() {
        BrightnessParams params = new BrightnessParams();
        params.screenBrightness = 0.5f;
    }

    void noScreenBrightnessSet() {
        BrightnessParams params = new BrightnessParams();
        int x = 42;
    }
}