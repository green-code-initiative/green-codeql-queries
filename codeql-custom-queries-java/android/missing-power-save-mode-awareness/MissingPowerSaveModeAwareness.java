// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) { return null; }
}
class Bundle {}
class Intent {}
class IntentFilter {
    public IntentFilter(String action) {}
}
class BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {}
}

class Activity extends Context {
    protected void onCreate(Bundle b) {}
}
class Service extends Context {}
class Application extends Context {
    public void onCreate() {}
}

class PowerManager {
    public static final String ACTION_POWER_SAVE_MODE_CHANGED = "android.os.action.POWER_SAVE_MODE_CHANGED";

    public boolean isPowerSaveMode() { return false; }
}

// NON-COMPLIANT — no power save mode awareness
class BadActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        // Does work but never checks power save mode
        System.out.println("started");
    }
}

// COMPLIANT — calls isPowerSaveMode()
class GoodActivityApi extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        PowerManager pm = (PowerManager) getSystemService("power");
        if (pm.isPowerSaveMode()) {
            // reduce activity
        }
    }
}

// COMPLIANT — registers for ACTION_POWER_SAVE_MODE_CHANGED via field reference
class GoodActivityBroadcastField extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        IntentFilter filter = new IntentFilter(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // adapt behavior
            }
        }, filter);
    }
}

// COMPLIANT — registers for ACTION_POWER_SAVE_MODE_CHANGED via string literal
class GoodActivityBroadcastString extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        IntentFilter filter = new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // adapt behavior
            }
        }, filter);
    }
}

public class MissingPowerSaveModeAwareness {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
