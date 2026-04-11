// Stubs
class File {
    public boolean delete() { return false; }
    public boolean deleteRecursively() { return false; }
}

class Context {
    public File getCacheDir() { return null; }
}

class Bundle {}
class Application extends Context {
    public void onCreate() {}
}
class Activity extends Context {
    protected void onCreate(Bundle b) {}
}
class Service extends Context {}

// NON-COMPLIANT — never cleans cache
class BadActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        // does work but never clears cache
    }
}

// COMPLIANT — clears cache with deleteRecursively()
class GoodActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getCacheDir().deleteRecursively();
    }
}

// COMPLIANT — clears cache with delete()
class AlsoGoodActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        File cacheDir = getCacheDir();
        cacheDir.delete();
    }
}

public class MissingCacheDirectoryCleanup {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
