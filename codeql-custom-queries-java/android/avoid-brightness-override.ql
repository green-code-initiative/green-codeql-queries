/**
 * @name Brightness Override
 * @description Setting WindowManager.LayoutParams#screenBrightness to BRIGHTNESS_OVERRIDE_FULL
 *              disables the adaptive brightness feature introduced in Android 9.
 *              Adaptive brightness adjusts screen brightness based on environment light
 *              to improve battery life. Overriding it is a very bad idea.
 * @kind problem
 * @problem.severity warning
 * @id java/android/avoid-brightness-override
 * @tags android
 * @tags java
 */

import java

from FieldWrite fw
where
  fw.getField().getName() = "screenBrightness" and
  exists(FieldRead fr |
    fr.getField().getName() = "BRIGHTNESS_OVERRIDE_FULL" and
    fw.getEnclosingCallable() = fr.getEnclosingCallable()
  )
select fw,
  "Avoid setting screenBrightness to BRIGHTNESS_OVERRIDE_FULL. This disables Android's adaptive brightness feature which was introduced to improve battery life."