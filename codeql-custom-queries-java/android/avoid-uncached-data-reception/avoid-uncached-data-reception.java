// Stub minimal pour HttpResponseCache
class UncachedDataCache {
    static Object install(Object directory, long maxSize) { return null; }
}

// 🚫 Noncompliant - Not using HttpResponseCache
class NoncompliantUncachedRequest {
    void fetchData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

            // $ Alert: No cache installed
            connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ✅ Compliant - Using HttpResponseCache
class CompliantCachedRequest {
    void fetchCachedData() {
        try {
            // OK: Cache installed at startup
            java.io.File cacheDir = new java.io.File("cache");
            UncachedDataCache.install(cacheDir, 1024 * 1024); // 1MB

            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

            connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 🚫 Noncompliant - Not using HttpResponseCache
class NoncompliantUncachedMethodRequest {
    void fetchDataFromServer() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

            // $ Alert: No cache installed
            int responseCode = connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ✅ Compliant - Using HttpResponseCache in a static block
class CompliantStaticCachedRequest {
    static {
        // OK: Cache installed in static block
        java.io.File cacheDir = new java.io.File("cache");
        UncachedDataCache.install(cacheDir, 1024 * 1024); // 1MB
    }

    void fetchCachedData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();

            connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}