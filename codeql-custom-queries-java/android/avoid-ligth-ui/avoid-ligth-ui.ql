/**
 * @name Ligth UI
 * @description Using a light theme (Theme.*.Light) increases energy consumption on (AM)OLED screens.
 *              By default Android sets a dark style — switching to a light theme should be avoided.
 *              Custom resources with bright colors or high-luminance bitmaps should also be avoided.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-ligth-ui
 * @tags android
 * @tags java
 * 
 * @note Test contains those for the day nigth theme detection too
 */

import semmle.code.xml.XML

from XmlAttribute attr
where
  attr.getName() = "parent" and
  attr.getValue().matches("%Light%") and
  attr.getElement().getName() = "style"
select attr,
  "Avoid using light themes ('" + attr.getValue() + "'). Light themes consume more energy on (AM)OLED screens. Prefer a dark theme instead."