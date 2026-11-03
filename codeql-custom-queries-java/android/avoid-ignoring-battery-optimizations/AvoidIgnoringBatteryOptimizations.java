// Stubs
class Manifest {
    static class permission {
        public static final String REQUEST_IGNORE_BATTERY_OPTIMIZATIONS =
                "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
        public static final String REQUEST_COMPANION_RUN_IN_BACKGROUND =
                "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND";
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

class Intent {
    public Intent() {}
    public Intent(String action) {}
    public Intent setAction(String action) { return this; }
    public Intent setData(Object uri) { return this; }
}

class Settings {
    public static final String ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS =
            "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
    public static final String ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS =
            "android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS";
}

class Uri {
    public static Uri parse(String uriString) { return null; }
}

class PowerManager {
    public boolean isIgnoringBatteryOptimizations(String packageName) { return false; }
}

// NON-COMPLIANT — references REQUEST_IGNORE_BATTERY_OPTIMIZATIONS via constant field
class BadIgnoreOptApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS}, 1);
    }
}

// NON-COMPLIANT — references the permission via string literal
class AlsoBadIgnoreOptApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{"android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"}, 1);
    }
}

// NON-COMPLIANT — checks the permission via ContextCompat
class CheckingBadIgnoreOpt extends Activity {
    void checkIt() {
        int result = ContextCompat.checkSelfPermission(
                this, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
    }
}

// NON-COMPLIANT — uses the string literal in checkSelfPermission
class CheckingBadIgnoreOptString extends Activity {
    void checkIt() {
        int result = checkSelfPermission(
                "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
    }
}

// NON-COMPLIANT — stores permission in a variable then uses it
class IndirectBadIgnoreOptApp extends Activity {
    private static final String PERM = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;

    void setup() {
        requestPermissions(new String[]{PERM}, 1);
    }
}

// NON-COMPLIANT — uses the intent action to request ignore battery optimizations
class IntentBadIgnoreOptApp extends Activity {
    void requestIgnore() {
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:com.example.app"));
    }
}

// COMPLIANT — uses ACCESS_FINE_LOCATION, not the ignore battery permission
class GoodApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
}

// COMPLIANT — uses a different companion permission
class AnotherGoodApp extends Activity {
    void setup() {
        requestPermissions(
                new String[]{Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND}, 1);
    }
}

// COMPLIANT — no permission usage at all
class NeutralApp extends Activity {
    void doWork() {
        System.out.println("No battery optimization bypass here");
    }
}

// COMPLIANT — uses the general battery optimization settings page (not the per-app bypass)
class SettingsPageApp extends Activity {
    void openSettings() {
        Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
    }
}

public class AvoidIgnoringBatteryOptimizations {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
