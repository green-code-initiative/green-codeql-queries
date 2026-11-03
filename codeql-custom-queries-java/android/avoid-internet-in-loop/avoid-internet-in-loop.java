class URL {
    Object openConnection() { return null; }
}

class HttpURLConnection {
    void connect() {}
    void disconnect() {}
    int getResponseCode() { return 200; }
}

// ============================================================
// 🚫 Noncompliant - openConnection() inside while loop
// ============================================================
class NoncompliantInternetInWhileLoop {

    void fetchInWhileLoop() throws Exception {
        int i = 0;
        while (i < 10) {
            URL url = new URL();
            url.openConnection(); // $ Alert
            i++;
        }
    }
}

// ============================================================
// 🚫 Noncompliant - openConnection() inside for loop
// ============================================================
class NoncompliantInternetInForLoop {

    void fetchInForLoop() throws Exception {
        for (int i = 0; i < 10; i++) {
            URL url = new URL();
            url.openConnection(); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - openConnection() inside do-while loop
// ============================================================
class NoncompliantInternetInDoWhileLoop {

    void fetchInDoWhileLoop() throws Exception {
        int i = 0;
        do {
            URL url = new URL();
            url.openConnection(); // $ Alert
            i++;
        } while (i < 10);
    }
}

// ============================================================
// 🚫 Noncompliant - openConnection() inside for-each loop
// ============================================================
class NoncompliantInternetInForEachLoop {

    void fetchInForEachLoop() throws Exception {
        String[] endpoints = {"http://a.com", "http://b.com"};
        for (String endpoint : endpoints) {
            URL url = new URL();
            url.openConnection(); // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - openConnection() inside nested loop
// ============================================================
class NoncompliantInternetInNestedLoop {

    void fetchInNestedLoop() throws Exception {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                URL url = new URL();
                url.openConnection(); // $ Alert
            }
        }
    }
}

// ============================================================
// ✅ Compliant - openConnection() outside loop
// ============================================================
class CompliantInternetUsageOutsideLoop {

    void fetchOnce() throws Exception {
        URL url = new URL();
        url.openConnection(); // OK
    }
}

// ============================================================
// ✅ Compliant - openConnection() before loop
// ============================================================
class CompliantInternetUsageBeforeLoop {

    void fetchBeforeLoop() throws Exception {
        URL url = new URL();
        url.openConnection(); // OK
        for (int i = 0; i < 10; i++) {
            // traitement sans nouvelle connexion
            int x = i * 2;
        }
    }
}

// ============================================================
// ✅ Compliant - no openConnection() in loop
// ============================================================
class CompliantInternetUsageNoConnectionInLoop {

    void processItems() throws Exception {
        String[] items = {"a", "b", "c"};
        for (String item : items) {
            // traitement sans ouvrir de connexion
            String result = item.toUpperCase(); // OK
        }
    }
}