/**
 * @name Day Night Mode
 * @description Dark theme is available in Android 10 (API level 29) and higher.
 *              Apps should support Dark theme by inheriting from a DayNight theme
 *              (parent="Theme.*.DayNight"). Failing to do so prevents the app from
 *              adapting to the user's system dark mode preference, increasing energy
 *              consumption on (AM)OLED screens.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-day-night-mode
 * @tags android
 * @tags java
 * 
 * @note Test contains those for the darktheme detection too
 */

import semmle.code.xml.XML

from XmlAttribute attr
where
  attr.getName() = "parent" and
  not attr.getValue().matches("%DayNight%") and
  attr.getElement().getName() = "style"
select attr,
  "The theme '" + attr.getElement().getAttribute("name").getValue() +
  "' does not inherit from a DayNight theme. Use 'Theme.*.DayNight' as parent to support Android dark mode and reduce energy consumption on (AM)OLED screens."