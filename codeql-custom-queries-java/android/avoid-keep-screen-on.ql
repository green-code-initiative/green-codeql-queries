/**
 * @name Detection of "Keep Screen On" flags
 * @description Identifies usage of FLAG_KEEP_SCREEN_ON in code or android:keepScreenOn in XML layouts.
 * @kind problem
 * @problem.severity warning
 * @id java/android/avoid-keep-screen-on
 * @link https://green-code-initiative.org/rules#id:GCI505
 * @link https://green-code-initiative.org/rules#id:GCI506
 * @tags android
 * @tags java
 */

import java
import semmle.code.xml.XML

predicate screenOnUsage(File f, string msg, Location loc) {
  exists(FieldAccess fa, Field field |
    field.hasName("FLAG_KEEP_SCREEN_ON") and
    field.getDeclaringType().getAnAncestor().getName() = "LayoutParams" and
    fa.getField() = field and
    loc = fa.getLocation() and
    f = loc.getFile() and
    msg = "The screen is kept on via FLAG_KEEP_SCREEN_ON in code."
  )
  or
  exists(XmlAttribute attr |
    attr.getName() = "keepScreenOn" and
    attr.getValue() = "true" and
    loc = attr.getLocation() and
    f = loc.getFile() and
    msg = "The screen is kept on via the 'android:keepScreenOn=\"true\"' XML attribute."
  )
}

from File f, string msg, Location loc
where screenOnUsage(f, msg, loc)
select loc, msg