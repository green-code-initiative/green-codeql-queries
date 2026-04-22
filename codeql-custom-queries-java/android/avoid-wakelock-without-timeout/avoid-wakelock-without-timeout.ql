/**
 * @name WakeLock acquired without a timeout
 * @description A wake lock is a mechanism to indicate that your application needs to have the device stay on. The general principle is to obtain a wake lock, acquire it and finally release it. Hence, the challenge here is to release the lock as soon as possible to avoid running down the device's battery excessively. Missing call to PowerManager#release() is a built-in check of Android lint (Wakelock check) but that does not prevent abuse of the lock over too long a period of time. This can be avoided by a call to PowerManager.WakeLock#acquire(long timeout) instead of PowerManager.WakeLock#acquire(), because the lock will be released for sure after the given timeout expires.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-wakelock-without-timeout
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class WakeLockAcquireWithoutTimeout extends MethodCall {
  WakeLockAcquireWithoutTimeout() {
    this.getMethod().hasName("acquire") and
    this.getMethod().getNumberOfParameters() = 0 and
    this.getMethod().getDeclaringType*().hasQualifiedName("", "PowerManager$WakeLock")
  }
}

from WakeLockAcquireWithoutTimeout call
select call,
  "PowerManager.WakeLock.acquire() is called without a timeout. " +
    "This holds the wake lock indefinitely and risks excessive battery drain " +
    "if the lock is not released promptly. Consider using acquire(long timeout) " +
    "instead to ensure the wake lock is automatically released after a bounded period."
