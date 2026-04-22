// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}

class Activity extends Context {}

class View {
    public View(Context context) {}
}

class SurfaceView extends View {
    public SurfaceView(Context context) { super(context); }
}

class GLSurfaceView extends SurfaceView {
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    public static final int RENDERMODE_CONTINUOUSLY = 1;

    public GLSurfaceView(Context context) { super(context); }
    public void setRenderMode(int renderMode) {}
    public void setRenderer(Object renderer) {}
}

// NON-COMPLIANT — explicitly sets RENDERMODE_CONTINUOUSLY via constant field
class BadGLView extends Activity {
    void setup() {
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); // BAD
    }
}

// NON-COMPLIANT — uses the raw int value 1 (RENDERMODE_CONTINUOUSLY)
class BadGLViewRawInt extends Activity {
    void setup() {
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderMode(1); // BAD
    }
}

// NON-COMPLIANT — stores the constant in a variable then passes it
class IndirectBadGLView extends Activity {
    void setup() {
        GLSurfaceView view = new GLSurfaceView(this);
        int mode = GLSurfaceView.RENDERMODE_CONTINUOUSLY;
        view.setRenderMode(mode); // BAD — argument resolves to RENDERMODE_CONTINUOUSLY
    }
}

// NON-COMPLIANT — subclass calls setRenderMode(RENDERMODE_CONTINUOUSLY) on itself
class BadGLSubclass extends GLSurfaceView {
    BadGLSubclass(Context context) {
        super(context);
    }

    void init() {
        setRenderMode(RENDERMODE_CONTINUOUSLY); // BAD
    }
}

// COMPLIANT — uses RENDERMODE_WHEN_DIRTY
class GoodGLView extends Activity {
    void setup() {
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); // GOOD
    }
}

// COMPLIANT — uses the raw int value 0 (RENDERMODE_WHEN_DIRTY)
class GoodGLViewRawInt extends Activity {
    void setup() {
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderMode(0); // GOOD
    }
}

// COMPLIANT — subclass calls setRenderMode(RENDERMODE_WHEN_DIRTY)
class GoodGLSubclass extends GLSurfaceView {
    GoodGLSubclass(Context context) {
        super(context);
    }

    void init() {
        setRenderMode(RENDERMODE_WHEN_DIRTY); // GOOD
    }
}

// COMPLIANT — never calls setRenderMode at all
class NeutralGLView extends Activity {
    void setup() {
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(null);
    }
}

// COMPLIANT — unrelated class with a method named setRenderMode
class SomeOtherRenderer {
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public void setRenderMode(int mode) {}
}

class UnrelatedCaller {
    void doSomething() {
        SomeOtherRenderer r = new SomeOtherRenderer();
        r.setRenderMode(1); // GOOD — not GLSurfaceView
    }
}

public class AvoidRendermodeContinuously {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
