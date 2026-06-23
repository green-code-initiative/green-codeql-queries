/**
 * @name Avoid HTTP calls inside loops
 * @description Performing HTTP calls inside loops can trigger a large number of network requests, increasing latency, CPU, network and server resource consumption unnecessarily. Prefer batching requests or moving the HTTP call outside the loop.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/spring/avoid-http-call-in-loop
 * @tags spring
 * @tags java
 */

import java

predicate isHttpClientCall(MethodCall call) {
  exists(string methodName |
    methodName = call.getMethod().getName() and
    (
      (
        methodName in ["getForObject", "postForObject", "exchange", "getForEntity", "postForEntity"] and
        call.getMethod().getDeclaringType*().hasQualifiedName("org.springframework.web.client", "RestTemplate")
      )
      or
      (
        methodName in ["retrieve", "exchangeToMono", "exchangeToFlux"] and
        call.getMethod().getDeclaringType*().hasQualifiedName("org.springframework.web.reactive.function.client", "WebClient")
      )
    )
  )
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
      streamCall.getMethod().getName() = "map" or
      streamCall.getMethod().getName() = "peek"
    ) and
    streamCall.getAnArgument() = lambda and
    call.getEnclosingCallable() = lambda.asMethod()
  )
}

from MethodCall call
where
  isHttpClientCall(call) and
  (
    isInsideLoop(call)
    or
    isInsideStreamOperation(call)
  )
select call,
  "HTTP call is performed inside a loop or stream operation. Prefer batching requests or moving the HTTP call outside the iteration to reduce network, CPU and energy consumption."