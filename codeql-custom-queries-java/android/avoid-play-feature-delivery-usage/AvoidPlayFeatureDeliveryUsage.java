// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}
class Bundle {}
class Application extends Context {
    public void onCreate() {}
}

class SplitInstallManagerFactory {
    public static SplitInstallManager create(Context context) { return null; }
}

class SplitInstallManager {
    public Object startInstall(SplitInstallRequest request) { return null; }
}

class SplitInstallRequest {
    public static SplitInstallRequest newBuilder() { return null; }
    public SplitInstallRequest addModule(String name) { return this; }
    public SplitInstallRequest build() { return this; }
}

// NON-COMPLIANT — no Play Feature Delivery usage
class BadApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}

// COMPLIANT — uses SplitInstallManager
class GoodApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SplitInstallManager manager = SplitInstallManagerFactory.create(this);
        SplitInstallRequest request = SplitInstallRequest.newBuilder()
                .addModule("camera_feature")
                .build();
        manager.startInstall(request);
    }
}

public class AvoidPlayFeatureDeliveryUsage {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
