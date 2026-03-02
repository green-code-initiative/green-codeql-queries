import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public class UnnecessaryTreeMapOrLinkedHashMapUsage {

    public void testTreeMap() {
        // $ Alert
        Map<String, String> treeMap = new TreeMap<>();
    }

    public void testLinkedHashMap() {
        // $ Alert
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
    }

    public void testHashMap() {
        // No alert expected
        Map<String, String> hashMap = new HashMap<>();
    }

    public void testTreeMapWithComparator() {
        // $ Alert
        Map<String, String> treeMapWithComp = new TreeMap<>((a, b) -> a.compareTo(b));
    }

    public void testLinkedHashMapWithCapacity() {
        // $ Alert
        Map<String, String> linkedHashMapWithCap = new LinkedHashMap<>(16, 0.75f, true);
    }
}
