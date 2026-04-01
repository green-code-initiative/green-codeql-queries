/**
 * @name High Frame Rate
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

from MethodCall mc, FloatLiteral frameRate
where
  mc.getMethod().getName() = "setFrameRate" and
  frameRate = mc.getArgument(0) and
  frameRate.getValue().toFloat() > 60.0
select mc,
  "Avoid setting frame rate above 60Hz (current value: " + frameRate.getValue() +
  "f). Higher refresh rates increase energy consumption on the display."