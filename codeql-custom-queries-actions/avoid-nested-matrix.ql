/**
 * @name Avoid nested matrix in GitHub Actions
 * @description Nested matrices in GitHub Actions workflows multiply job combinations exponentially, leading to unnecessary CI/CD resource consumption and increased energy usage.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id actions/avoid-nested-matrix
 */

import actions
import codeql.actions.ast.internal.Yaml
import codeql.actions.ast.internal.Ast

from Job j, StrategyImpl s, YamlMapping matrixNode, YamlMapping innerMatrix
where
  s.getParentNode() = j and
  matrixNode = s.getNode().lookup("matrix") and
  innerMatrix = matrixNode.getAChildNode+() and
  exists(innerMatrix.lookup("matrix"))
select j, "Job '" + j.getId() + "' contains a nested matrix. Nested matrices multiply job combinations exponentially, increasing CI/CD resource consumption and energy usage. Flatten the matrix or reduce the number of dimensions."