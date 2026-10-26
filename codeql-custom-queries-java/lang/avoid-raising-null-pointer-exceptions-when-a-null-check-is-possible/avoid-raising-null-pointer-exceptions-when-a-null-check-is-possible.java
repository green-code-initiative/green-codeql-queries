class nonCompliantNullCheck {
    void nullCheckTest(String str) {
        try {
            String part = str.substring(5); // Noncompliant
            System.out.println(part);
        } catch (NullPointerException e) {
            System.out.println("String is null");
        }
    }

    void anotherNullCheckTest(Object obj) {
        try {
            obj.toString(); // Noncompliant
        } catch (NullPointerException e) {
            System.out.println("Object is null");
        }
    }

    void multiCatchTest(String str) {
        try {
            int length = str.length(); // Noncompliant
        } catch (NullPointerException | StringIndexOutOfBoundsException e) {
            System.out.println("Error occurred");
        }
    }
}

class compliantNullCheck {
    void nullCheckTest(String str) {
        if (str != null && str.length() > 5) { // Compliant
            String part = str.substring(5);
            System.out.println(part);
        } else {
            System.out.println("String is null or too short");
        }
    }

    void anotherNullCheckTest(Object obj) {
        if (obj != null) { // Compliant
            obj.toString();
        }
    }
}