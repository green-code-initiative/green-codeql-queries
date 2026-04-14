// Stub classes to avoid redeclaration conflicts

package android.widget;

class AutoCompleteTextView {
    public AutoCompleteTextView() {}
}

class MultiAutoCompleteTextView {
    public MultiAutoCompleteTextView() {}
}

class EditText {
    public EditText() {}
}

// ============================================================
// 🚫 Noncompliant - AutoCompleteTextView instantiation
// ============================================================
class AvoidAutoCompleteNoncompliant {

    void setupSearchField() {
        AutoCompleteTextView actv = new AutoCompleteTextView(); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - MultiAutoCompleteTextView instantiation
// ============================================================
class AvoidMultiAutoCompleteNoncompliant {

    void setupMultiSearchField() {
        MultiAutoCompleteTextView mactv = new MultiAutoCompleteTextView(); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - both violations in one method
// ============================================================
class AvoidAutoCompleteNoncompliantBoth {

    void setupBothFields() {
        AutoCompleteTextView actv = new AutoCompleteTextView(); // $ Alert
        MultiAutoCompleteTextView mactv = new MultiAutoCompleteTextView(); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - simple EditText with hint
// ============================================================
class AvoidAutoCompleteCompliant {

    void setupSimpleField() {
        EditText et = new EditText(); // OK
    }
}