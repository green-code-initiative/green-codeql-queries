class ListView {
    void setAdapter(Object adapter) {}
    void scrollTo(int x, int y) {}
}

class GridView {
    void setAdapter(Object adapter) {}
    void setNumColumns(int numColumns) {}
}

class RecyclerView {
    void setAdapter(Object adapter) {}
    void setLayoutManager(Object layoutManager) {}
    void scrollToPosition(int position) {}
}

class NoncompliantLazyLoading {

    // 🚫 Noncompliant - ListView field
    ListView listView = null; // $ Alert

    // 🚫 Noncompliant - GridView field
    GridView gridView = null; // $ Alert

    // 🚫 Noncompliant - RecyclerView field
    RecyclerView recyclerView = null; // $ Alert

    void setupList() {
        // 🚫 Noncompliant - local ListView
        ListView list = new ListView(); // $ Alert
        list.setAdapter(null);
    }

    void setupGrid() {
        // 🚫 Noncompliant - local GridView
        GridView grid = new GridView(); // $ Alert
        grid.setNumColumns(3);
    }

    void setupRecycler() {
        // 🚫 Noncompliant - local RecyclerView
        RecyclerView recycler = new RecyclerView(); // $ Alert
        recycler.setLayoutManager(null);
        recycler.setAdapter(null);
    }

    void setupMultiple() {
        // 🚫 Noncompliant - multiple views in same method
        ListView l = null;      // $ Alert
        GridView g = null;      // $ Alert
        RecyclerView r = null;  // $ Alert
    }
}

class CompliantLazyLoading {
    // ✅ Compliant - no classic Android views
    void doNothing() {}
}