// Stubs
class Context {
    public Object startService(Intent intent) { return null; }
    public Object startForegroundService(Intent intent) { return null; }
}
class Intent {
    public Intent(Context ctx, Class<?> cls) {}
    public String getAction() { return null; }
}

class BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {}
}

// NON-COMPLIANT — starts a service from onReceive()
class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, Object.class));
    }
}

// NON-COMPLIANT — starts a foreground service from onReceive()
class BootForegroundReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startForegroundService(new Intent(context, Object.class));
    }
}

// COMPLIANT — does not start any service
class SafeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Just logs or does lightweight work
        System.out.println("Received broadcast");
    }
}

// COMPLIANT — has startService but NOT in onReceive
class AnotherReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Received");
    }

    public void someOtherMethod(Context context) {
        context.startService(new Intent(context, Object.class));
    }
}

public class AvoidSilentServiceOnBoot {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
