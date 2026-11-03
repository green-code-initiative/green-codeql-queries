// ============================================================
// Noncompliant cases
// ============================================================

class NoncompliantForLoop {
    void test() {
        String result = "";
        for (int i = 0; i < 10; i++) {
            result += i; // $ Alert
        }
    }
}

class NoncompliantEnhancedForLoop {
    void test() {
        String[] items = {"a", "b", "c"};
        String result = "";
        for (String item : items) {
            result += item; // $ Alert
        }
    }
}

class NoncompliantWhileLoop {
    void test() {
        String result = "";
        int i = 0;
        while (i < 10) {
            result += i; // $ Alert
            i++;
        }
    }
}

// ============================================================
// Compliant cases
// ============================================================

class CompliantStringBuilder {
    void test() {
        String[] items = {"a", "b", "c"};
        StringBuilder sb = new StringBuilder();
        for (String item : items) {
            sb.append(item); // OK
        }
        String result = sb.toString();
    }
}

class CompliantConcatOutsideLoop {
    void test() {
        String result = "hello" + " " + "world"; // OK
        result += "!"; // OK - not in a loop
    }
}

class CompliantIntAddInLoop {
    void test() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i; // OK - not a String
        }
    }
}
