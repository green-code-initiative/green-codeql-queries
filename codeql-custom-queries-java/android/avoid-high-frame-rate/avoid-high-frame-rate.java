// Stubs en default package — getName() suffit pour le matching

class Surface {
    void setFrameRate(float frameRate, int compatibility) {}
}

class SurfaceCompatibility {
    static final int FRAME_RATE_COMPATIBILITY_DEFAULT = 0;
    static final int FRAME_RATE_COMPATIBILITY_FIXED_SOURCE = 1;
}

class LayoutParams {
    public float preferredRefreshRate;
    public float screenBrightness;
}

class Window {
    private LayoutParams attrs = new LayoutParams();
    public LayoutParams getAttributes() { return attrs; }
    public void setAttributes(LayoutParams p) { this.attrs = p; }
}

class Activity {
    private Window w = new Window();
    public Window getWindow() { return w; }
}

// ============================================================
// CAS 1 — setFrameRate() > 60f -> DOIT etre flagge
// ============================================================

class NoncompliantFrameRate90 {
    void set90Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(90f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

class NoncompliantFrameRate120 {
    void set120Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(120f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

class NoncompliantFrameRate144 {
    void set144Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(144f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

class NoncompliantFrameRateJustAbove {
    void setJustAbove60Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(60.1f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
    }
}

class NoncompliantFrameRateInCondition {
    void setHighFrameRateIfSupported(boolean supported) {
        Surface surface = new Surface();
        if (supported) {
            surface.setFrameRate(120f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
        }
    }
}

class NoncompliantFrameRateInLoop {
    void setHighFrameRateInLoop() {
        Surface surface = new Surface();
        for (int i = 0; i < 3; i++) {
            surface.setFrameRate(90f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // $ Alert
        }
    }
}

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
// CAS 2 — preferredRefreshRate > 60f -> DOIT etre flagge
// ============================================================

class NoncompliantPreferredRefreshRate120 extends Activity {
    void setHighRefreshRate() {
        LayoutParams params = getWindow().getAttributes();
        params.preferredRefreshRate = 120.0f; // $ Alert
        getWindow().setAttributes(params);
    }
}

class NoncompliantPreferredRefreshRate90 extends Activity {
    void setHighRefreshRate() {
        LayoutParams params = getWindow().getAttributes();
        params.preferredRefreshRate = 90.0f; // $ Alert
        getWindow().setAttributes(params);
    }
}

class NoncompliantPreferredRefreshRateJustAbove extends Activity {
    void setJustAbove() {
        LayoutParams params = getWindow().getAttributes();
        params.preferredRefreshRate = 60.1f; // $ Alert
        getWindow().setAttributes(params);
    }
}

class NoncompliantPreferredRefreshRateInCondition extends Activity {
    void setConditionally(boolean supported) {
        if (supported) {
            LayoutParams params = getWindow().getAttributes();
            params.preferredRefreshRate = 120.0f; // $ Alert
            getWindow().setAttributes(params);
        }
    }
}

// ============================================================
// CAS 3 — setFrameRate() <= 60f -> NE DOIT PAS etre flagge
// ============================================================

class CompliantFrameRate60 {
    void set60Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(60f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // OK
    }
}

class CompliantFrameRate30 {
    void set30Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(30f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_DEFAULT); // OK
    }
}

class CompliantFrameRate24 {
    void set24Hz() {
        Surface surface = new Surface();
        surface.setFrameRate(24f, SurfaceCompatibility.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE); // OK
    }
}

// ============================================================
// CAS 4 — preferredRefreshRate <= 60f -> NE DOIT PAS etre flagge
// ============================================================

class CompliantPreferredRefreshRate60 extends Activity {
    void set60Hz() {
        LayoutParams params = getWindow().getAttributes();
        params.preferredRefreshRate = 60.0f; // OK
        getWindow().setAttributes(params);
    }
}

class CompliantPreferredRefreshRate30 extends Activity {
    void set30Hz() {
        LayoutParams params = getWindow().getAttributes();
        params.preferredRefreshRate = 30.0f; // OK
        getWindow().setAttributes(params);
    }
}

// ============================================================
// CAS 5 — Ecriture sur screenBrightness (autre champ)
//          -> NE DOIT PAS etre flagge
// ============================================================

class CompliantOtherField extends Activity {
    void setBrightness() {
        LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.75f; // OK — autre champ, pas de fps
        getWindow().setAttributes(params);
    }
}

// ============================================================
// CAS 6 — Aucun appel lie au frame rate -> NE DOIT PAS etre flagge
// ============================================================

class CompliantNoFrameRate {
    void doSomethingElse() {
        int x = 42;
        String msg = "no frame rate here";
    }
}