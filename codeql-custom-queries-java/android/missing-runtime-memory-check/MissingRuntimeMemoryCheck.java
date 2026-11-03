// Stubs
class Context {
    public static final String ACTIVITY_SERVICE = "activity";
    public Object getSystemService(String name) { return null; }
}
class Bundle {}
class Activity extends Context { protected void onCreate(Bundle b) {} }
class Service extends Context {}
class Application extends Context {}
class ActivityManager {
    public boolean isLowRamDevice() { return false; }
    public int getMemoryClass() { return 256; }
}
class Bitmap {
    public enum Config { ARGB_8888 }
    public static Bitmap createBitmap(int w, int h, Config c) { return null; }
    public Bitmap copy(Config c, boolean mutable) { return null; }
}
class BitmapFactory {
    public static Bitmap decodeFile(String path) { return null; }
}
class LruCache<K, V> {
    public LruCache(int maxSize) {}
    public void resize(int maxSize) {}
    public V put(K key, V value) { return null; }
}

// NON-COMPLIANT — doit déclencher des alertes
class BadActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        LruCache<String, Bitmap> cache = new LruCache<>(50 * 1024 * 1024);
        cache.put("hero", BitmapFactory.decodeFile("/sdcard/hero.png"));
        Bitmap large = Bitmap.createBitmap(4096, 4096, Bitmap.Config.ARGB_8888);
    }
}

// COMPLIANT — aucune alerte
class GoodActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        boolean lowRam = am.isLowRamDevice();
        int memClass = am.getMemoryClass();
        int size = lowRam ? 10 * 1024 * 1024 : memClass / 4 * 1024 * 1024;
        LruCache<String, Bitmap> cache = new LruCache<>(size);
        cache.put("hero", BitmapFactory.decodeFile("/sdcard/hero.png"));
        if (!lowRam) {
            Bitmap large = Bitmap.createBitmap(4096, 4096, Bitmap.Config.ARGB_8888);
        }
    }
}

public class MissingRuntimeMemoryCheck {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
