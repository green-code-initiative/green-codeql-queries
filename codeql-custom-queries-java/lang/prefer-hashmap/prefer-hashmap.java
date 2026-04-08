import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

// ============================================================
// 🚫 Noncompliant - TreeMap used where ordering is unnecessary
// ============================================================
class NoncompliantTreeMapUsage {

    void testTreeMap() {
        Map<String, String> treeMap = new TreeMap<>(); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - LinkedHashMap used where insertion order is unnecessary
// ============================================================
class NoncompliantLinkedHashMapUsage {

    void testLinkedHashMap() {
        Map<String, String> linkedHashMap = new LinkedHashMap<>(); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - TreeMap with comparator still unnecessarily ordered
// ============================================================
class NoncompliantTreeMapWithComparatorUsage {

    void testTreeMapWithComparator() {
        Map<String, String> treeMapWithComp = new TreeMap<>((a, b) -> a.compareTo(b)); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - LinkedHashMap with capacity still tracks insertion order
// ============================================================
class NoncompliantLinkedHashMapWithCapacityUsage {

    void testLinkedHashMapWithCapacity() {
        Map<String, String> linkedHashMapWithCap = new LinkedHashMap<>(16, 0.75f, true); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - HashMap used, no unnecessary ordering
// ============================================================
class CompliantHashMapUsage {

    void testHashMap() {
        Map<String, String> hashMap = new HashMap<>(); // OK
    }
}