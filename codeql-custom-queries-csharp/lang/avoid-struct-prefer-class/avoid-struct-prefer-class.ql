/**
 * @name Prefer class over struct
 * @description Structs are value types copied on every assignment and method call, generating unnecessary CPU and memory overhead. Prefer class (reference type) unless the type is small, immutable, and short-lived.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id csharp/lang/prefer-class-over-struct
 * @tags lang
 * @tags csharp
 */

import csharp

from Struct s
where s.getFile().getExtension() = "cs" // necessary to avoid analysing dll files
select s, "Prefer 'class' over 'struct' for '" + s.getName() + "'. Structs are copied on every assignment and method call, increasing CPU usage and memory pressure. Use a class (reference type) instead, unless this type is small, immutable, and short-lived."