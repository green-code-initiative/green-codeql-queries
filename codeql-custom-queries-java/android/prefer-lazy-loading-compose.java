// Stubs
class ListView {}
class GridView {}
class RecyclerView {}
class LazyColumn {}
class LazyRow {}
class LazyVerticalGrid {}
class LazyHorizontalGrid {}

// 🚫 Noncompliant - using eager loading views
class NoncompliantLazyLoading {

    ListView listView = null;        // $ Alert
    GridView gridView = null;        // $ Alert
    RecyclerView recyclerView = null; // $ Alert

    void setupViews() {
        ListView list = null;        // $ Alert
        GridView grid = null;        // $ Alert
        RecyclerView recycler = null; // $ Alert
    }
}

// ✅ Compliant - using Jetpack Compose lazy components
class CompliantLazyLoading {

    LazyColumn lazyColumn = null;          // OK
    LazyRow lazyRow = null;                // OK
    LazyVerticalGrid lazyGrid = null;      // OK
    LazyHorizontalGrid lazyHGrid = null;   // OK
}