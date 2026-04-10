// ============================================================
// Stub - uniquement HttpURLConnection qui n'est pas instanciable
// ============================================================

class HttpURLConnection {
    void setDoOutput(boolean b) {}
    java.io.OutputStream getOutputStream() { return null; }
}

class FakeHttpURLConnection extends HttpURLConnection {}

// ============================================================
// 🚫 Noncompliant - Using raw OutputStream for HTTP request
// ============================================================
class NoncompliantHttpRequest {

    void sendData() {
        try {
            HttpURLConnection connection = new FakeHttpURLConnection();
            connection.setDoOutput(true);

            java.io.OutputStream os = connection.getOutputStream(); // $ Alert

            os.write("Hello".getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// ✅ Compliant - Using GZIPOutputStream for HTTP request
// ============================================================
class CompliantHttpRequest {

    void sendCompressedData() {
        try {
            HttpURLConnection connection = new FakeHttpURLConnection();
            connection.setDoOutput(true);

            java.util.zip.GZIPOutputStream gzipOs = new java.util.zip.GZIPOutputStream(connection.getOutputStream());
            gzipOs.write("Hello".getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// 🚫 Noncompliant - Using raw OutputStream with BufferedOutputStream
// ============================================================
class NoncompliantBufferedRequest {

    void sendBufferedData() {
        try {
            HttpURLConnection connection = new FakeHttpURLConnection();
            connection.setDoOutput(true);

            java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(connection.getOutputStream()); // $ Alert

            bos.write("Hello".getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// ✅ Compliant - Using GZIPOutputStream with BufferedOutputStream
// ============================================================
class CompliantBufferedRequest {

    void sendCompressedBufferedData() {
        try {
            HttpURLConnection connection = new FakeHttpURLConnection();
            connection.setDoOutput(true);

            java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(
                new java.util.zip.GZIPOutputStream(connection.getOutputStream())
            );
            bos.write("Hello".getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// 🚫 Noncompliant - Using raw OutputStream in a loop
// ============================================================
class NoncompliantLoopRequest {

    void sendMultipleData() {
        try {
            HttpURLConnection connection = new FakeHttpURLConnection();
            connection.setDoOutput(true);

            java.io.OutputStream os = connection.getOutputStream(); // $ Alert

            for (int i = 0; i < 10; i++) {
                os.write(("Data " + i).getBytes());
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// ✅ Compliant - Using GZIPOutputStream in a loop
// ============================================================
class CompliantLoopRequest {

    void sendCompressedMultipleData() {
        try {
            HttpURLConnection connection = new FakeHttpURLConnection();
            connection.setDoOutput(true);

            java.util.zip.GZIPOutputStream gzipOs = new java.util.zip.GZIPOutputStream(connection.getOutputStream());

            for (int i = 0; i < 10; i++) {
                gzipOs.write(("Data " + i).getBytes());
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

// ============================================================
// ✅ Compliant - getOutputStream on a non-HttpURLConnection class
// ============================================================
class CustomStream {
    java.io.OutputStream getOutputStream() { return null; }
}

class CompliantNonHttpOutputStream {

    void writeToCustomStream() {
        try {
            CustomStream custom = new CustomStream();

            // OK: getOutputStream is NOT from HttpURLConnection
            java.io.OutputStream os = custom.getOutputStream();
            os.write("Hello".getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}