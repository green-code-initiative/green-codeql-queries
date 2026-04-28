/**
 * @name Avoid High Frame Rate
 * @description Calling Surface#setFrameRate() with a frameRate greater than 60f raises the
 *              display refresh rate to 90Hz or 120Hz, which increases energy consumption.
 *              A regular app should not exceed 60 frames per second to optimize battery life.
 * @kind problem
 * @problem.severity warning
 * @id java/android/avoid-high-frame-rate
 * @link https://green-code-initiative.org/rules#id:GCI531
 * @tags android
 * @tags java
 */

import java

from Expr e, FloatLiteral rate, string msg
where
  (
    e.(MethodCall).getMethod().getName() = "setFrameRate" and
    rate = e.(MethodCall).getArgument(0) and
    rate.getFloatValue() > 60.0 and
    msg = "Avoid setting frame rate above 60Hz (current value: " + rate.getValue() +
          "f). Higher refresh rates increase energy consumption on the display."
  )
  or
  (
    e.(AssignExpr).getSource().getUnderlyingExpr() = rate and
    rate.getFloatValue() > 60.0 and
    e.(AssignExpr).getDest().toString().matches("%preferredRefreshRate%") and
    msg = "Avoid setting preferredRefreshRate above 60Hz (current value: " + rate.getValue() +
          "f). Higher refresh rates increase energy consumption on the display."
  )
select e, msg