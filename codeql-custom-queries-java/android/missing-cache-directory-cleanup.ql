/**
 * @name Missing cache directory cleanup
 * @description  	A good (but quite rare) practice in the long term is to delete the whole cache directory of the app onto the target device. This requires the source code of the app to contains at least one call to the method Context.cacheDir#deleteRecursively().
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/missing-cache-directory-cleanup
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class AndroidComponent extends Class {
  AndroidComponent() {
    exists(Class ancestor | ancestor = this.getASupertype*() |
      ancestor.hasName("Activity") or
      ancestor.hasName("Service") or
      ancestor.hasName("Application")
    ) and
    not this.hasName("Activity") and
    not this.hasName("Service") and
    not this.hasName("Application") and
    not this.hasName("Context")
  }
}

class GetCacheDirCall extends MethodCall {
  GetCacheDirCall() {
    this.getMethod().hasName("getCacheDir")
  }
}

class DeleteRecursivelyCall extends MethodCall {
  DeleteRecursivelyCall() {
    this.getMethod().hasName("deleteRecursively") or
    this.getMethod().hasName("delete")
  }
}

class CacheCleanupPattern extends MethodCall {
  CacheCleanupPattern() {
    this instanceof DeleteRecursivelyCall and
    exists(GetCacheDirCall getCacheDir |
      getCacheDir.getEnclosingCallable() = this.getEnclosingCallable()
    )
  }
}

from AndroidComponent component
where
  not exists(CacheCleanupPattern cleanup |
    cleanup.getEnclosingCallable().getDeclaringType() = component
  )
select component,
  "Component " + component.getName() +
    " never clears the cache directory. Consider calling getCacheDir().deleteRecursively() periodically to reduce storage footprint."
