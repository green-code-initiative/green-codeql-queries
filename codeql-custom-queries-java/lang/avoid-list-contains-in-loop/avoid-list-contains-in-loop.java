import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ============================================================
// Noncompliant cases
// ============================================================

class NoncompliantForEachLoop {
    void test() {
        List<String> blocked = new ArrayList<>();
        String[] items = {"a", "b", "c"};
        for (String item : items) {
            if (blocked.contains(item)) {} // $ Alert
        }
    }
}

class NoncompliantWhileLoop {
    void test() {
        List<String> blocked = new ArrayList<>();
        int i = 0;
        while (i < 10) {
            if (blocked.contains("x")) {} // $ Alert
            i++;
        }
    }
}

class NoncompliantTraditionalForLoop {
    void test() {
        List<String> blocked = new ArrayList<>();
        String[] items = {"a", "b", "c"};
        for (int i = 0; i < items.length; i++) {
            if (blocked.contains(items[i])) {} // $ Alert
        }
    }
}

// ============================================================
// Compliant cases
// ============================================================

class CompliantSetInLoop {
    void test() {
        Set<String> blocked = new HashSet<>();
        String[] items = {"a", "b", "c"};
        for (String item : items) {
            if (blocked.contains(item)) {} // OK - Set has O(1) lookup
        }
    }
}

class CompliantContainsOutsideLoop {
    void test() {
        List<String> blocked = new ArrayList<>();
        if (blocked.contains("spam")) {} // OK - not inside a loop
    }
}
