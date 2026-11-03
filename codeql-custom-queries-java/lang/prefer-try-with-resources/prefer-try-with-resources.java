class Resource implements AutoCloseable {
    Resource(String path) {}

    @Override
    public void close() {}
}

// ============================================================
// Noncompliant cases
// ============================================================

class NoncompliantNoTry {
    void test() throws Exception {
        Resource r = new Resource("file.txt"); // $ Alert
        r.close();
    }
}

class NoncompliantInsideRegularTry {
    void test() {
        try {
            Resource r = new Resource("file.txt"); // $ Alert
            r.close();
        } catch (Exception e) {}
    }
}

// ============================================================
// Compliant cases
// ============================================================

class CompliantTryWithResources {
    void test() throws Exception {
        try (Resource r = new Resource("file.txt")) {
            // use r - will be closed automatically
        }
    }
}
