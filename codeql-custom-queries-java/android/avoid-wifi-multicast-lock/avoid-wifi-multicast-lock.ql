/**
 * @name Unreleased Wifi Multicast Lock
 * @description Acquiring a WifiManager.MulticastLock with `acquire()` allows the device to receive multicast packets,
 *              which can cause significant battery drain. The lock must be released with `release()` when no longer needed.
 *              Failing to do so can lead to uncontrolled energy consumption.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-wifi-multicast-lock
 * @link https://green-code-initiative.org/rules#id:GCI503
 * @tags android
 * @tags java
 */

import java

predicate isMulticastLockType(RefType t) {
  // Production : type qualifié Android
  t.hasQualifiedName("android.net.wifi", "WifiManager$MulticastLock")
  or
  // Stub de test : inner class dans le package par défaut
  t.getName() = "MulticastLock" and
  t.getEnclosingType().getName() = "WifiManager"
}

predicate isMulticastLockCall(MethodCall mc, Variable lock) {
  isMulticastLockType(mc.getMethod().getDeclaringType()) and
  mc.getQualifier() = lock.getAnAccess()
}

from MethodCall acquireCall, Variable lock
where
  acquireCall.getMethod().getName() = "acquire" and
  isMulticastLockCall(acquireCall, lock) and
  not exists(MethodCall releaseCall |
    releaseCall.getMethod().getName() = "release" and
    isMulticastLockCall(releaseCall, lock) and
    releaseCall.getEnclosingCallable() = acquireCall.getEnclosingCallable()
  )
select acquireCall,
  "WifiManager.MulticastLock.acquire() is called here without a corresponding release() in the same method. This can cause significant battery drain."