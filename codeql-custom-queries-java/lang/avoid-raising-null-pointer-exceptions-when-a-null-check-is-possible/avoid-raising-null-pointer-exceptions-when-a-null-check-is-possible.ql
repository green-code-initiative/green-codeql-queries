/**
 * @name Avoid raising NullPointerExceptions when a null check is possible
 * @description Identifies try-catch blocks that handle NullPointerException. Null checks should be performed to avoid NullPointerExceptions rather than relying on exception handling.
 * @kind problem
 * @problem.severity recommendation
 * @precision high
 * @id java/avoid-raising-null-pointer-exceptions-when-a-null-check-is-possible
 * @tags java/lang/avoid-raising-null-pointer-exceptions-when-a-null-check-is-possible
 */

import java

pragma[nomagic]
predicate isNullPointerException(RefType rt) {
  rt.hasQualifiedName("java.lang", "NullPointerException")
}

private RefType caughtType(TryStmt try, int index) {
  exists(CatchClause cc | cc = try.getCatchClause(index) |
    if cc.isMultiCatch()
    then result = cc.getVariable().getTypeAccess().(UnionTypeAccess).getAnAlternative().getType()
    else result = cc.getVariable().getType()
  )
}

from TryStmt try, int index, RefType npeType
where
  try.getFile().isJavaSourceFile() and
  npeType = caughtType(try, index) and
  isNullPointerException(npeType)
select try.getCatchClause(index),
  "This catch-clause handles NullPointerException. Consider using null checks instead of relying on exception handling to avoid performance overhead."