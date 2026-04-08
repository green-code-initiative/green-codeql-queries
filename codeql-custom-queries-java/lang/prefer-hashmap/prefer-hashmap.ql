/**
 * @name Unnecessary TreeMap or LinkedHashMap usage
 * @description Detects instantiations of TreeMap or LinkedHashMap where a HashMap could suffice.
 * @kind problem
 * @problem.severity warning
 * @id java/lang/prefer-hashmap
 * @tags java
 * @tags lang
 */

import java

from ClassInstanceExpr newExpr, RefType t
where
  t = newExpr.getConstructedType() and
  (
    t.getName().matches("TreeMap%") or
    t.getName().matches("LinkedHashMap%")
  ) and
  t.getPackage().getName() = "java.util"
select newExpr,
  "Warning: you may use a HashMap instead of " + t.getName()
