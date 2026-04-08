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

from MethodCall acquireCall
where
  acquireCall.getMethod().getName() = "acquire" and
  not exists(MethodCall releaseCall |
    releaseCall.getEnclosingCallable() = acquireCall.getEnclosingCallable() and
    releaseCall.getMethod().getName() = "release"
  )
select acquireCall,
  "WifiManager.MulticastLock.acquire() is called here without a corresponding release() in the same method. This can cause significant battery drain."
