// Stubs
class Manifest {
    static class permission {
        public static final String REQUEST_COMPANION_RUN_IN_BACKGROUND =
                "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND";
        public static final String REQUEST_COMPANION_USE_DATA_IN_BACKGROUND =
                "android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND";
        public static final String ACCESS_FINE_LOCATION =
                "android.permission.ACCESS_FINE_LOCATION";
    }
}

class Context {
    public int checkSelfPermission(String permission) { return 0; }
}

class Activity extends Context {
    public void requestPermissions(String[] permissions, int requestCode) {}
}

class PackageManager {
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_DENIED = -1;
}

class ContextCompat {
    public static int checkSelfPermission(Context context, String permission) { return 0; }
}

class ActivityCompat {
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {}
}

class CompanionDeviceManager {
    public void associate(Object request, Object callback, Object handler) {}
}

// NON-COMPLIANT — references REQUEST_COMPANION_RUN_IN_BACKGROUND via constant field
class BadCompanionApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND}, 1);
    }
}

// NON-COMPLIANT — references the permission via string literal
class AlsoBadCompanionApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{"android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND"}, 1);
    }
}

// NON-COMPLIANT — checks the permission via ContextCompat
class CheckingBadPermission extends Activity {
    void checkIt() {
        int result = ContextCompat.checkSelfPermission(
                this, Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND);
    }
}

// NON-COMPLIANT — uses the string literal in checkSelfPermission
class CheckingBadPermissionString extends Activity {
    void checkIt() {
        int result = checkSelfPermission(
                "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND");
    }
}

// NON-COMPLIANT — stores permission in a variable then uses it
class IndirectBadCompanionApp extends Activity {
    private static final String PERM = Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND;

    void setup() {
        requestPermissions(new String[]{PERM}, 1);
    }
}

// COMPLIANT — uses ACCESS_FINE_LOCATION, not the companion background permission
class GoodApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
}

// COMPLIANT — uses REQUEST_COMPANION_USE_DATA_IN_BACKGROUND (different permission)
class AnotherGoodApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{Manifest.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND}, 1);
    }
}

// COMPLIANT — no permission usage at all
class NeutralApp extends Activity {
    void doWork() {
        System.out.println("No companion device usage here");
    }
}

public class AvoidCompanionInBackground {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
