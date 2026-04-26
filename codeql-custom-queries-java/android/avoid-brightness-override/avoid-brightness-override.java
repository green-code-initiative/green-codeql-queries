// Stubs Android en default package — pas besoin de sous-dossiers.
// La query utilise getName() au lieu de hasQualifiedName() donc
// le package exact n'a pas d'importance pour le matching.

// ---------------------------------------------------------------------------
// Stubs minimaux
// ---------------------------------------------------------------------------

class LayoutParams {
    public float screenBrightness;
    public int flags;

    public static final float BRIGHTNESS_OVERRIDE_FULL = 1.0f;
    public static final float BRIGHTNESS_OVERRIDE_NONE = -1.0f;
    public static final int FLAG_FULLSCREEN = 0x00000400;
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

// ---------------------------------------------------------------------------
// CAS 1 — Helper setBrightness(float value)
//          L'ecriture interne utilise le parametre "value"
//          -> isBrightnessSetterImpl() = true pour setBrightness()
//          -> L'implementation interne NE DOIT PAS etre flaggee
//          -> Les 4 call sites DOIVENT etre flagges
// ---------------------------------------------------------------------------

class HelperSetterActivity extends Activity {

    // Implementation : value = parametre -> reconnu comme helper -> PAS flagge
    private void setBrightness(float value) {
        LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = value; // NE DOIT PAS etre flagge
        getWindow().setAttributes(params);
    }

    public void callSite1() {
        setBrightness(0.75f); // $ problem
    }

    public void callSite2() {
        setBrightness(1.0f); // $ problem
    }

    public void callSite3() {
        setBrightness(LayoutParams.BRIGHTNESS_OVERRIDE_FULL); // $ problem
    }

    public void callSite4() {
        setBrightness(LayoutParams.BRIGHTNESS_OVERRIDE_NONE); // $ problem
    }
}

// ---------------------------------------------------------------------------
// CAS 2 — Helper avec un nom different (applyScreenBrightness)
//          Meme logique que CAS 1 : reconnu par isBrightnessSetterImpl()
//          car la valeur ecrite = parametre
//          -> Implementation PAS flaggee, call site flagge
// ---------------------------------------------------------------------------

class RenamedHelperActivity extends Activity {

    // Nom different mais meme pattern -> reconnu comme helper -> PAS flagge
    private void applyScreenBrightness(float level) {
        LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = level; // NE DOIT PAS etre flagge
        getWindow().setAttributes(lp);
    }

    // Call site -> flagge
    public void configure() {
        applyScreenBrightness(0.5f); // $ problem
    }
}

// ---------------------------------------------------------------------------
// CAS 3 — Aucune ecriture sur screenBrightness
//          -> RIEN ne doit etre flagge
// ---------------------------------------------------------------------------

class NoBrightnessActivity extends Activity {

    // Lecture seule -> pas de FieldWrite -> pas de flag
    public float readBrightness() {
        LayoutParams params = getWindow().getAttributes();
        return params.screenBrightness; // NE DOIT PAS etre flagge
    }

    // Ecriture sur un autre champ -> champ != screenBrightness -> pas de flag
    public void setFlags() {
        LayoutParams params = getWindow().getAttributes();
        params.flags = LayoutParams.FLAG_FULLSCREEN; // NE DOIT PAS etre flagge
        getWindow().setAttributes(params);
    }

    // Methode sans interaction avec la luminosite -> pas de flag
    public void doNothing() {
        int x = 1 + 1;
    }
}

// ---------------------------------------------------------------------------
// CAS 4 — Heritage : helper defini dans la classe parente
//          -> Implementation parente NE DOIT PAS etre flaggee
//          -> Call site dans la classe enfant DOIT etre flagge
// ---------------------------------------------------------------------------

class BaseActivity extends Activity {

    // Implementation dans le parent -> parametre -> helper -> PAS flagge
    protected void setBrightness(float value) {
        LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = value; // NE DOIT PAS etre flagge
        getWindow().setAttributes(params);
    }
}

class ChildActivity extends BaseActivity {

    // Appel du helper herite -> call site -> flagge
    public void setup() {
        setBrightness(0.9f); // $ problem
    }
}

// ---------------------------------------------------------------------------
// CAS 5 — Double helper (chain) : limite documentee a 1 niveau
//
//   applyRawBrightness(float v) : ecrit lp.screenBrightness = v
//     -> helper direct reconnu par isBrightnessSetterImpl() -> PAS flagge
//
//   setNightMode() : appelle applyRawBrightness(0.2f)
//     -> call site du helper direct -> DOIT etre flagge
//
//   enableNight() : appelle setNightMode()
//     -> setNightMode() n'ecrit pas directement screenBrightness
//     -> non reconnu comme helper par isBrightnessSetterImpl()
//     -> NON flagge (limite documentee : query a 1 seul niveau de transitivite)
// ---------------------------------------------------------------------------

class ChainedHelperActivity extends Activity {

    // Helper direct : ecriture via parametre -> PAS flagge
    private void applyRawBrightness(float value) {
        LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = value; // NE DOIT PAS etre flagge
        getWindow().setAttributes(lp);
    }

    // Call site du helper direct -> flagge
    private void setNightMode() {
        applyRawBrightness(0.2f); // $ problem
    }

    // Appel de setNightMode() : non reconnu comme helper -> NON flagge
    public void enableNight() {
        setNightMode(); // NE DOIT PAS etre flagge (limite 1 niveau)
    }
}