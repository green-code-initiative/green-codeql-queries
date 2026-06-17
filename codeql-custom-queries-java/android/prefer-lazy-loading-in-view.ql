/**
 * @name Prefer Lazy Loading in View
 * @description ListView, GridView and RecyclerView load all data eagerly, increasing memory usage and energy consumption. Use Jetpack Compose lazy components (LazyColumn, LazyRow, LazyVerticalGrid) which only load data when visible on screen.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/android/prefer-lazy-loading-in-view
 * @tags android
 * @tags kotlin
 */

import java

from Variable v, RefType t
where
  v.getType() = t and
  (
    t.getName() = "ListView" or
    t.getName() = "GridView" or
    t.getName() = "RecyclerView"
  )
select v,
  "Avoid '" + t.getName() + "'. Use Jetpack Compose lazy components (LazyColumn, LazyRow, LazyVerticalGrid) from androidx.compose.foundation.lazy instead to load data only when visible on screen."
