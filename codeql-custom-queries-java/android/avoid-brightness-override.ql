/**
 * @name Avoid Brightness Override
 * @description Calling setBrightness() or directly setting screenBrightness
 *              forces a fixed screen brightness, bypassing Android's adaptive
 *              brightness feature (introduced in Android 9) which adjusts
 *              brightness based on ambient light to improve battery life.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @link https://green-code-initiative.org/rules#id:GCI522
 * @id java/android/avoid-brightness-override
 * @tags android
 * @tags java
 */

import java

predicate isBrightnessSetterImpl(Method m) {
  exists(FieldWrite fw |
    fw.getField().getName() = "screenBrightness" and
      fw.getField().getDeclaringType().getName() = "LayoutParams" and
    fw.getEnclosingCallable() = m
  )
}

predicate callsBrightnessSetter(MethodCall mc) {
  isBrightnessSetterImpl(mc.getMethod())
}

predicate directBrightnessWrite(FieldWrite fw) {
  fw.getField().getName() = "screenBrightness" and
  fw.getField().getDeclaringType().getName() = "LayoutParams" and
  not isBrightnessSetterImpl(fw.getEnclosingCallable())
}

from Expr e
where
    callsBrightnessSetter(e)
  or
    directBrightnessWrite(e) 
select e + "Calling setBrightness() or directly setting screenBrightness forces a fixed screen brightness, bypassing Android's adaptive brightness feature (introduced in Android 9) which adjusts brightness based on ambient light to improve battery life."