/**
 * @name Avoid Thrifty Notification
 * @description When constructing notifications with NotificationCompat.Builder, avoid calling
 *              setVibrate() or setSound() on the builder. Default notification settings are
 *              usually enough. For API 31+, prefer configuring vibration on NotificationChannel
 *              by using setVibrationPattern().
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-thrifty-notification
 * @link https://green-code-initiative.org/rules#id:GCI529
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  mc.getMethod().getName() in ["setVibrate", "setSound"]
select mc,
  "Avoid calling '" + mc.getMethod().getName() + "' on NotificationCompat.Builder. Default notification settings are enough. For API 31+, consider using NotificationChannel#setVibrationPattern() instead."