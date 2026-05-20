/**
 * @name Avoid unpaginated repository findAll
 * @description Calling repository.findAll() without a Pageable argument loads all records from
 *              the database into memory, which can exhaust the heap and trigger costly garbage
 *              collection cycles on large tables, wasting memory, CPU, and energy.
 *              Use findAll(Pageable) to limit the result set.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/spring/avoid-unpaginated-repository-findall
 * @tags spring
 * @tags java
 */

import java

predicate isRepositoryCall(MethodCall call) {
  call.getQualifier().getType().(RefType).getASupertype*().getName() = "Repository"
  or
  call.getQualifier().getType().(RefType).getName().matches("%Repository")
}

from MethodCall findAllCall
where
  findAllCall.getMethod().getName() = "findAll" and
  findAllCall.getNumArgument() = 0 and
  isRepositoryCall(findAllCall)
select findAllCall,
  "repository.findAll() with no arguments loads all records from the database into memory. Use findAll(Pageable) to limit the result set and avoid memory exhaustion and unnecessary energy consumption."
