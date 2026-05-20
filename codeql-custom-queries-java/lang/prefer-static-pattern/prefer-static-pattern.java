class Pattern {
    static Pattern compile(String regex) { return null; }
    boolean matches(String input) { return false; }
}

// ============================================================
// Noncompliant cases
// ============================================================

class NoncompliantInstanceMethod {
    boolean validate(String input) {
        Pattern p = Pattern.compile("\\d+"); // $ Alert
        return p.matches(input);
    }
}

class NoncompliantStaticMethod {
    static boolean validate(String input) {
        Pattern p = Pattern.compile("\\d+"); // $ Alert
        return p.matches(input);
    }
}

class NoncompliantConstructor {
    Pattern p;
    NoncompliantConstructor(String regex) {
        this.p = Pattern.compile(regex); // $ Alert
    }
}

// ============================================================
// Compliant cases
// ============================================================

class CompliantStaticField {
    private static final Pattern DIGITS = Pattern.compile("\\d+"); // OK

    boolean validate(String input) {
        return DIGITS.matches(input);
    }
}

class CompliantStaticBlock {
    private static final Pattern DIGITS;
    static {
        DIGITS = Pattern.compile("\\d+"); // OK
    }

    boolean validate(String input) {
        return DIGITS.matches(input);
    }
}
