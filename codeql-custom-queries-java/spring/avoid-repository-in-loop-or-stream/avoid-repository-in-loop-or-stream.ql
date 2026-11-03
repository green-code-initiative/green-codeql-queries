/**
 * @name Avoid Spring repository call in loop or stream operation
 * @description Calling a Spring repository method inside a loop or stream operation (forEach, forEachOrdered, peek, map)
 *              triggers one database query per iteration instead of a single batch call, causing unnecessary CPU and
 *              database load and superfluous energy consumption. Prefer batch methods such as findAllById().
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/spring/avoid-repository-call-in-loop-or-stream
 * @tags spring
 * @tags java
 */

import java

predicate isRepositoryCall(MethodCall call) {
  call.getQualifier().getType().(RefType).getASupertype*().getName() = "Repository"
  or
  call.getQualifier().getType().(RefType).getName().matches("%Repository")
}

predicate isInsideLoop(MethodCall call) {
  exists(LoopStmt loop |
    loop.getBody().getAChild*() = call.getEnclosingStmt()
  )
}

predicate isInsideStreamOperation(MethodCall call) {
  exists(MethodCall streamCall, LambdaExpr lambda |
    (
      streamCall.getMethod().getName() = "forEach" or
      streamCall.getMethod().getName() = "forEachOrdered" or
      streamCall.getMethod().getName() = "peek" or
      streamCall.getMethod().getName() = "map"
    ) and
    streamCall.getAnArgument() = lambda and
    call.getEnclosingCallable() = lambda.asMethod()
  )
}

from MethodCall repoCall
where
  isRepositoryCall(repoCall) and
  (isInsideLoop(repoCall) or isInsideStreamOperation(repoCall))
select repoCall,
  "Spring repository method '" + repoCall.getMethod().getName() +
    "()' is called inside a loop or stream operation. Prefer a single batch call outside the loop to reduce database round-trips and energy consumption."
