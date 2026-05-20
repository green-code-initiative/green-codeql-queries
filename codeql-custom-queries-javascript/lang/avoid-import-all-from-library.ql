/**
 * @name Avoid importing all from a library
 * @description Importing everything from a library increases the overall size of the program, which increases memory and storage space requirements. This is especially critical for mobile devices and web applications where bandwidth and download times matter. Smaller programs have better runtime performance as fewer modules need to be interpreted or compiled.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id js/lang/avoid-import-all-from-library
 * @tags javascript
 * @tags typescript
 * @tags lang
 */

import javascript

predicate isBulkImport(ImportDeclaration decl) {
  exists(BulkImportDeclaration spec |  decl = spec )
  or
  decl.getASpecifier().toString() = "_"
}

predicate isGlobalImport(ImportDeclaration decl) {
  decl.getRawImportPath() = decl.getASpecifier().toString()
}

from ImportDeclaration decl
where
  isBulkImport(decl)
or
  isGlobalImport(decl)
select decl, "Avoid importing entire modules. Import only the specific functions or components you need to reduce bundle size and improve runtime performance."
