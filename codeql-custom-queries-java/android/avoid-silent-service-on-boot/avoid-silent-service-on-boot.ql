/**
 * @name Avoid silent service start on boot
 * @description Service@Boot-time 	Services are long-living operations, as components of the apps. However, they can be started in isolation each time the device is next started, without the user's acknowledgement. This technique should be discouraged because the accumulation of these silent services results in excessive battery depletion that remains unexplained from the end-user's point of view. In addition, end-users know how to kill applications, but more rarely how to kill services. Thus, any developer should avoid having a call to Context#startService() from a Broadcast Receiver component that has specified an intent-filter for the BOOT_COMPLETED or QUICKBOOT_POWERON action in the manifest.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-silent-service-on-boot
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class BroadcastReceiverSubclass extends Class {
  BroadcastReceiverSubclass() {
    this.getASupertype*().hasName("BroadcastReceiver") and
    not this.hasName("BroadcastReceiver")
  }
}

class StartServiceCall extends MethodCall {
  StartServiceCall() {
    this.getMethod().hasName("startService") or
    this.getMethod().hasName("startForegroundService")
  }
}

from BroadcastReceiverSubclass receiver, StartServiceCall call
where
  call.getEnclosingCallable().getDeclaringType() = receiver and
  call.getEnclosingCallable().(Method).hasName("onReceive")
select call,
  "BroadcastReceiver " + receiver.getName() +
    " calls startService() from onReceive(). If this receiver listens for BOOT_COMPLETED, " +
    "this silently starts a service at boot, draining battery. Consider using JobScheduler or WorkManager."
