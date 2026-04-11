// Stubs
class Context {
    public Object getSystemService(String name) { return null; }
}
class Bundle {}
class Application extends Context {
    protected void attachBaseContext(Context base) {}
    public void onCreate() {}
}
class MultiDexApplication extends Application {}
class MultiDex {
    public static void install(Context context) {}
}

// NON-COMPLIANT — extends MultiDexApplication
class BadApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}

// NON-COMPLIANT — calls MultiDex.install()
class BadApp2 extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

// COMPLIANT — no multidex usage
class GoodApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}

public class MissingMultidexInFatApp {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
