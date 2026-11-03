// Minimal stubs for Android SDK classes not available in javac
class WifiManager {
    class MulticastLock {
        void acquire() {}
        void release() {}
    }
}

// 🚫 Noncompliant - acquire without release
class Noncompliant1WifiMulticast {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        lock.acquire(); // $ Alert
    }
}

// 🚫 Noncompliant - acquire in a separate method without release
class Noncompliant2WifiMulticast {
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
class Noncompliant3WifiMulticast {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        for (int i = 0; i < 10; i++) {
            lock.acquire(); // $ Alert
        }
    }
}

// 🚫 Noncompliant - acquire in an inner class without release
class Noncompliant4WifiMulticast {
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

// 🚫 Noncompliant - only multicastLock1 is acquired without release
//                   multicastLock2 is properly acquired and released
class Noncompliant5WifiMulticast {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock multicastLock1 = manager.new MulticastLock();
        WifiManager.MulticastLock multicastLock2 = manager.new MulticastLock();
        multicastLock1.acquire(); // $ Alert - no matching release on this object
        multicastLock2.acquire(); // OK - released below
        multicastLock2.release();
    }
}

// ✅ Compliant - acquire with release
class Compliant1WifiMulticast {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock lock = manager.new MulticastLock();
        lock.acquire(); // OK
        lock.release(); // OK
    }
}

// ✅ Compliant - acquire and release in separate methods
class Compliant2WifiMulticast {
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
class Compliant3WifiMulticast {
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
class Compliant4WifiMulticast {
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

// ✅ Compliant - each lock is acquired and released on the same object
class Compliant5WifiMulticast {
    void method1() {
        WifiManager manager = new WifiManager();
        WifiManager.MulticastLock multicastLock1 = manager.new MulticastLock();
        WifiManager.MulticastLock multicastLock2 = manager.new MulticastLock();
        multicastLock1.acquire(); // OK
        multicastLock2.acquire(); // OK
        multicastLock1.release(); // OK
        multicastLock2.release(); // OK
    }
}