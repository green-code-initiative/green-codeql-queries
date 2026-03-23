// 🚫 Noncompliant - Using raw OutputStream for HTTP request
class NoncompliantHttpRequest {

    void sendData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            // $ Alert: Using raw OutputStream
            java.io.OutputStream os = connection.getOutputStream();

            os.write("Hello, world!".getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ✅ Compliant - Using GZIPOutputStream for HTTP request
class CompliantHttpRequest {

    void sendCompressedData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            // OK: Using GZIPOutputStream
            java.util.zip.GZIPOutputStream gzipOs = new java.util.zip.GZIPOutputStream(connection.getOutputStream());

            gzipOs.write("Hello, world!".getBytes());
            gzipOs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 🚫 Noncompliant - Using raw OutputStream with BufferedOutputStream
class NoncompliantBufferedRequest {

    void sendBufferedData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            // $ Alert: Using raw OutputStream (even with BufferedOutputStream)
            java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(connection.getOutputStream());

            bos.write("Hello, world!".getBytes());
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ✅ Compliant - Using GZIPOutputStream with BufferedOutputStream
class CompliantBufferedRequest {

    void sendCompressedBufferedData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            // OK: Using GZIPOutputStream with BufferedOutputStream
            java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(
                new java.util.zip.GZIPOutputStream(connection.getOutputStream())
            );

            bos.write("Hello, world!".getBytes());
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 🚫 Noncompliant - Using raw OutputStream in a loop
class NoncompliantLoopRequest {

    void sendMultipleData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            // $ Alert: Using raw OutputStream in a loop
            java.io.OutputStream os = connection.getOutputStream();

            for (int i = 0; i < 10; i++) {
                os.write(("Data " + i).getBytes());
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ✅ Compliant - Using GZIPOutputStream in a loop
class CompliantLoopRequest {

    void sendCompressedMultipleData() {
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            // OK: Using GZIPOutputStream in a loop
            java.util.zip.GZIPOutputStream gzipOs = new java.util.zip.GZIPOutputStream(connection.getOutputStream());

            for (int i = 0; i < 10; i++) {
                gzipOs.write(("Data " + i).getBytes());
            }
            gzipOs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
