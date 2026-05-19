/**
 * @name Avoid unpaginated repository access in Spring endpoints
 * @description Calling findAll() directly from a Spring REST endpoint without pagination can load an entire dataset into memory, increasing CPU, memory, network and database usage unnecessarily. Prefer Pageable and paginated queries.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/spring/avoid-unpaginated-repository-access
 * @tags spring
 * @tags java
 * @tags performance
 * @tags green
 */

import java

predicate hasSpringEndpointAnnotation(Method m) {
  exists(Annotation a |
    a = m.getAnAnnotation() and
    (
      a.getType().getName() = "GetMapping" or
      a.getType().getName() = "RequestMapping" or
      a.getType().getName() = "PostMapping" or
      a.getType().getName() = "PutMapping" or
      a.getType().getName() = "DeleteMapping"
    )
  )
}

predicate hasPageableParameter(Method m) {
  exists(Parameter p |
    p = m.getAParameter() and
    p.getType().getName() = "Pageable"
  )
}

predicate isRepositoryFindAllCall(MethodCall call) {
  call.getMethod().getName() = "findAll" and
  (
    call.getQualifier().getType().(RefType).getName().matches("%Repository")
    or
    call.getQualifier().getType().(RefType).getASupertype*().getName() = "Repository"
  )
}

from Method endpoint, MethodCall call
where
  hasSpringEndpointAnnotation(endpoint) and
  not hasPageableParameter(endpoint) and
  call.getEnclosingCallable() = endpoint and
  isRepositoryFindAllCall(call)
select call,
  "Spring repository findAll() is called from an endpoint without pagination. Prefer Pageable and paginated queries to reduce resource consumption."