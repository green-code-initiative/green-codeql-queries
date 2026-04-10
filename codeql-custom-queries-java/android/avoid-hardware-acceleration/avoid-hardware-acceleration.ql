/**
 * @name Hardware Acceleration
 * @description Hardware acceleration for 2D rendering is enabled by default for API level >= 14.
 *              It increases RAM usage and resource consumption. It should be explicitly disabled
 *              at the application or activity level using android:hardwareAccelerated="false"
 *              in the Android manifest, or programmatically.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-hardware-acceleration
 * @tags android
 * @tags java
 */

import semmle.code.xml.XML

from XmlAttribute attr
where
  attr.getName() = "hardwareAccelerated" and
  attr.getValue() = "true" and
  (
    attr.getElement().getName() = "application" or
    attr.getElement().getName() = "activity"
  )
select attr,
  "Hardware acceleration is explicitly enabled (android:hardwareAccelerated=\"true\") on <" +
  attr.getElement().getName() + ">. Consider disabling it to reduce RAM usage and energy consumption."