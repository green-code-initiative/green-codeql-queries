// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}

class Activity extends Context {}

class VoiceInteractionSession {
    public VoiceInteractionSession(Context context) {}
    public void setKeepAwake(boolean keepAwake) {}
    public void onCreate() {}
    public void onShow(Object args, int showFlags) {}
    public void onDestroy() {}
}

// NON-COMPLIANT — calls setKeepAwake(true) explicitly
class BadVoiceSession extends VoiceInteractionSession {
    BadVoiceSession(Context context) {
        super(context);
    }

    public void onCreate() {
        setKeepAwake(true); // BAD
    }
}

// NON-COMPLIANT — calls setKeepAwake(true) in onShow
class AnotherBadVoiceSession extends VoiceInteractionSession {
    AnotherBadVoiceSession(Context context) {
        super(context);
    }

    public void onShow(Object args, int showFlags) {
        setKeepAwake(true); // BAD
    }
}

// NON-COMPLIANT — calls setKeepAwake(true) via a variable
class IndirectBadVoiceSession extends VoiceInteractionSession {
    IndirectBadVoiceSession(Context context) {
        super(context);
    }

    public void onCreate() {
        boolean keep = true;
        setKeepAwake(keep); // BAD — argument is true
    }
}

// NON-COMPLIANT — calls setKeepAwake(true) on another session reference
class ExternalBadCaller {
    void enableKeepAwake(VoiceInteractionSession session) {
        session.setKeepAwake(true); // BAD
    }
}

// COMPLIANT — calls setKeepAwake(false), which is the energy-efficient choice
class GoodVoiceSession extends VoiceInteractionSession {
    GoodVoiceSession(Context context) {
        super(context);
    }

    public void onCreate() {
        setKeepAwake(false); // GOOD
    }
}

// COMPLIANT — calls setKeepAwake(false) on another session reference
class ExternalGoodCaller {
    void disableKeepAwake(VoiceInteractionSession session) {
        session.setKeepAwake(false); // GOOD
    }
}

// COMPLIANT — never calls setKeepAwake at all (relies on default, but no explicit true)
class NeutralVoiceSession extends VoiceInteractionSession {
    NeutralVoiceSession(Context context) {
        super(context);
    }

    public void onCreate() {
        System.out.println("No setKeepAwake call");
    }
}

// COMPLIANT — unrelated method named setKeepAwake on a different class
class SomeOtherClass {
    public void setKeepAwake(boolean keepAwake) {}
}

class UnrelatedCaller {
    void doSomething() {
        SomeOtherClass obj = new SomeOtherClass();
        obj.setKeepAwake(true); 
    }
}

public class AvoidKeepVoiceAwake {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
