/**
 * @name Uncached Data Reception
 * @description Detects if an HTTP call (getInputStream or getResponseCode) is not directly associated with UncachedDataCache.install() in the same method.
 * @kind problem
 * @problem.severity warning
 * @id java/android/uncached-data-reception
 * @tags android
 * @tags java
 */

import java

from MethodCall httpCall
where
  (
    httpCall.getMethod().hasName("getInputStream") or
    httpCall.getMethod().hasName("getResponseCode")
  ) and
  not exists(MethodCall installCall |
    installCall.getMethod().hasName("install") and
    (
      installCall.getEnclosingCallable() = httpCall.getEnclosingCallable()
      or
      installCall.getEnclosingCallable() instanceof StaticInitializer and
      installCall.getEnclosingCallable().getDeclaringType() =
        httpCall.getEnclosingCallable().getDeclaringType()
    )
  )
select httpCall,
  "Avoid not caching HTTP responses. Use UncachedDataCache.install() in the same method to cache responses and save energy."