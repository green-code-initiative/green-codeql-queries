/**
 * @name Torch-free
 * @description Programmatically enabling torch mode with CameraManager#setTorchMode(..., true)
 *              should be avoided as the torch will drain the battery unnecessarily.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-torch
 * @link https://green-code-initiative.org/rules#id:GCI530
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  mc.getMethod().getName() = "setTorchMode" and
  mc.getArgument(1).(BooleanLiteral).getBooleanValue() = true
select mc,
  "Avoid enabling torch mode programmatically. The torch drains the battery unnecessarily."