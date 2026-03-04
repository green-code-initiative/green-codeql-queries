// Stubs
class ListView
class GridView
class RecyclerView
class LazyColumn
class LazyRow
class LazyVerticalGrid
class LazyHorizontalGrid

// 🚫 Noncompliant - using eager loading views
class NoncompliantLazyLoading {

    val listView: ListView? = null         // $ Alert
    val gridView: GridView? = null         // $ Alert
    val recyclerView: RecyclerView? = null // $ Alert

    fun setupViews() {
        val list: ListView? = null         // $ Alert
        val grid: GridView? = null         // $ Alert
        val recycler: RecyclerView? = null // $ Alert
    }
}

// ✅ Compliant - using Jetpack Compose lazy components
class CompliantLazyLoading {

    val lazyColumn: LazyColumn? = null         // OK
    val lazyRow: LazyRow? = null               // OK
    val lazyGrid: LazyVerticalGrid? = null     // OK
    val lazyHGrid: LazyHorizontalGrid? = null  // OK
}