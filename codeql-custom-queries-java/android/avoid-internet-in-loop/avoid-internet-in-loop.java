class URL {
    Object openConnection() { return null; }
}

class HttpURLConnection {
    void connect() {}
    void disconnect() {}
    int getResponseCode() { return 200; }
}

// 🚫 Noncompliant - openConnection() inside loops
class NoncompliantInternetInLoop {

    void fetchInWhileLoop() throws Exception {
        int i = 0;
        while (i < 10) {
            URL url = new URL();
            url.openConnection(); // $ Alert
            i++;
        }
    }

    void fetchInForLoop() throws Exception {
        for (int i = 0; i < 10; i++) {
            URL url = new URL();
            url.openConnection(); // $ Alert
        }
    }

    void fetchInDoWhileLoop() throws Exception {
        int i = 0;
        do {
            URL url = new URL();
            url.openConnection(); // $ Alert
            i++;
        } while (i < 10);
    }

    void fetchInForEachLoop() throws Exception {
        String[] endpoints = {"http://a.com", "http://b.com"};
        for (String endpoint : endpoints) {
            URL url = new URL();
            url.openConnection(); // $ Alert
        }
    }

    void fetchInNestedLoop() throws Exception {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                URL url = new URL();
                url.openConnection(); // $ Alert
            }
        }
    }
}

// ✅ Compliant - openConnection() outside loops
class CompliantInternetUsage {

    void fetchOnce() throws Exception {
        URL url = new URL();
        url.openConnection();
    }

    void fetchBeforeLoop() throws Exception {
        URL url = new URL();
        url.openConnection();
        for (int i = 0; i < 10; i++) {
            // traitement sans nouvelle connexion
            int x = i * 2;
        }
    }

    void processItems() throws Exception {
        String[] items = {"a", "b", "c"};
        for (String item : items) {
            // traitement sans ouvrir de connexion
            String result = item.toUpperCase();
        }
    }
}