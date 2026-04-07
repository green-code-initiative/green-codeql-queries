/**
 * @name Avoid Everlasting Service
 * @description If someone calls Context#startService() then the service will continue
 *              running until Context#stopService() or Service#stopSelf() is called.
 *              Failing to call any of these methods can lead to uncontrolled energy leakage.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/android/avoid-everlasting-service
 * @tags android
 * @tags java
 */

import java

from MethodCall startCall
where
  startCall.getMethod().getName() = "startService" and
  not exists(MethodCall stopCall |
    stopCall.getEnclosingCallable() = startCall.getEnclosingCallable() and
    (
      stopCall.getMethod().getName() = "stopService"
      or
      stopCall.getMethod().getName() = "stopSelf"
    )
  )
select startCall,
  "startService() is called here without a corresponding stopService() or stopSelf() in the same method. This may keep the service alive indefinitely and cause uncontrolled energy leakage."