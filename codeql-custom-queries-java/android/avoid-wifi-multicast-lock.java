// Minimal stubs for Android SDK classes not available in javac
class WifiManager {
    class MulticastLock {
        void acquire() {}
        void release() {}
    }
}

// 🚫 Noncompliant - acquire without release
class Noncompliant1 {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        lock.acquire(); // $ Alert
    }
}

// 🚫 Noncompliant - acquire in a separate method without release
class Noncompliant2 {
    void method1() {
        acquireLock();
    }

    void acquireLock() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        lock.acquire(); // $ Alert
    }
}

// 🚫 Noncompliant - acquire in a loop without release
class Noncompliant3 {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        for (int i = 0; i < 10; i++) {
            lock.acquire(); // $ Alert
        }
    }
}

// 🚫 Noncompliant - acquire in an inner class without release
class Noncompliant4 {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lock.acquire(); // $ Alert
            }
        };
        runnable.run();
    }
}

// ✅ Compliant - acquire with release
class Compliant1 {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        lock.acquire(); // OK
        lock.release(); // OK
    }
}

// ✅ Compliant - acquire and release in separate methods
class Compliant2 {
    void method1() {
        acquireAndRelease();
    }

    void acquireAndRelease() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        lock.acquire(); // OK
        lock.release(); // OK
    }
}

// ✅ Compliant - acquire and release in a loop
class Compliant3 {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        for (int i = 0; i < 10; i++) {
            lock.acquire(); // OK
            lock.release(); // OK
        }
    }
}

// ✅ Compliant - acquire and release in an inner class
class Compliant4 {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lock.acquire(); // OK
                lock.release(); // OK
            }
        };
        runnable.run();
    }
}
