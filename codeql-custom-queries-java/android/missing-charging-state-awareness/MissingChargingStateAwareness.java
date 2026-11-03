// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) { return null; }
}
class Bundle {}
class Intent {
    public static final String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public static final String ACTION_BATTERY_LOW = "android.intent.action.BATTERY_LOW";
    public static final String ACTION_BATTERY_OKAY = "android.intent.action.BATTERY_OKAY";
}
class IntentFilter {
    public IntentFilter() {}
    public IntentFilter(String action) {}
    public void addAction(String action) {}
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

class BatteryManager {
    public boolean isCharging() { return false; }
    public int getIntProperty(int id) { return 0; }
}

// NON-COMPLIANT — no battery or charging monitoring at all
class BadActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        System.out.println("started");
    }
}

// COMPLIANT — monitors power connected/disconnected via field reference
class GoodActivityPowerField extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {}
        }, filter);
    }
}

// COMPLIANT — monitors battery low/okay via field reference
class GoodActivityBatteryField extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {}
        }, filter);
    }
}

// COMPLIANT — monitors via string literal
class GoodActivityStringLiteral extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        IntentFilter filter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {}
        }, filter);
    }
}

// COMPLIANT — uses BatteryManager API
class GoodActivityBatteryManager extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        BatteryManager bm = (BatteryManager) getSystemService("batterymanager");
        if (bm.isCharging()) {
            // do heavy work
        }
    }
}

public class MissingChargingStateAwareness {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
