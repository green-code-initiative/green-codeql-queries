/**
 * @name Avoid lazy-loaded entity access inside loops
 * @description Accessing lazy-loaded JPA/Hibernate relationships inside loops may trigger N+1 queries, increasing database, CPU and network usage unnecessarily. Consider JOIN FETCH, entity graphs, batch fetching or preloading related entities.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/hibernate/avoid-lazy-entity-access-in-loop
 * @tags java
 * @tags hibernate
 */

import java

predicate isJpaRelationAnnotation(Annotation annotation) {
  annotation.getType().getName() = "ManyToOne" or
  annotation.getType().getName() = "OneToMany" or
  annotation.getType().getName() = "OneToOne" or
  annotation.getType().getName() = "ManyToMany"
}

predicate isLazyJpaRelationAnnotation(Annotation annotation) {
  isJpaRelationAnnotation(annotation) and
  (
    annotation.toString().matches("%LAZY%")
    or
    annotation.getType().getName() = "OneToMany"
    or
    annotation.getType().getName() = "ManyToMany"
  )
}

predicate isGetterForField(Method getter, Field field) {
  field.getDeclaringType() = getter.getDeclaringType() and
  getter.getName().toLowerCase() = "get" + field.getName().toLowerCase()
}

predicate isLazyJpaRelationGetter(MethodCall call) {
  exists(Method getter, Field field, Annotation annotation |
    getter = call.getMethod() and
    isGetterForField(getter, field) and
    annotation = field.getAnAnnotation() and
    isLazyJpaRelationAnnotation(annotation)
  )
}

predicate isInsideLoop(MethodCall call) {
  exists(LoopStmt loop |
    loop.getBody().getAChild*() = call.getEnclosingStmt()
  )
}

from MethodCall call
where
  isLazyJpaRelationGetter(call) and
  isInsideLoop(call)
select call,
  "Lazy-loaded JPA/Hibernate relationship accessed inside a loop may trigger an N+1 query problem. Consider JOIN FETCH, entity graphs, batch fetching or preloading related entities."