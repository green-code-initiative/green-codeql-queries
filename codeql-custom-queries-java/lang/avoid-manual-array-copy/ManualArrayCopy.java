// ============================================================
// 🚫 Noncompliant - manual array copy using for loop
// ============================================================
class NoncompliantManualCopyForLoop {

    void testForLoop() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i]; // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - manual array copy using while loop
// ============================================================
class NoncompliantManualCopyWhileLoop {

    void testWhileLoop() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        int j = 0;
        while (j < src.length) {
            dest[j] = src[j]; // $ Alert
            j++;
        }
    }
}

// ============================================================
// 🚫 Noncompliant - manual array copy using enhanced for loop
// ============================================================
class NoncompliantManualCopyEnhancedForLoop {

    void testEnhancedForLoop() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        int index = 0;
        for (int value : src) {
            dest[index++] = value; // $ Alert
        }
    }
}

// ============================================================
// 🚫 Noncompliant - manual array copy inside conditional block
// ============================================================
class NoncompliantManualCopyInsideCondition {

    void testInsideCondition() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        if (dest.length > 0) {
            for (int k = 0; k < src.length; k++) {
                dest[k] = src[k]; // $ Alert
            }
        }
    }
}

// ============================================================
// ✅ Compliant - System.arraycopy used instead of manual loop
// ============================================================
class CompliantArrayCopySystemArraycopy {

    void testSystemArraycopy() {
        int[] src = {1, 2, 3, 4, 5};
        int[] dest = new int[5];

        System.arraycopy(src, 0, dest, 0, src.length); // OK
    }
}

// ============================================================
// ✅ Compliant - loop modifies values, not a plain copy
// ============================================================
class CompliantLoopWithTransformation {

    void testLoopWithTransformation() {
        int[] src = {1, 2, 3, 4, 5};

        for (int i = 0; i < src.length; i++) {
            src[i] = src[i] * 2; // OK
        }
    }
}

// ============================================================
// ✅ Compliant - loop initializes array to a constant, not a copy
// ============================================================
class CompliantLoopWithInitialization {

    void testLoopWithInitialization() {
        int[] dest = new int[5];

        for (int i = 0; i < dest.length; i++) {
            dest[i] = 0; // OK
        }
    }
}