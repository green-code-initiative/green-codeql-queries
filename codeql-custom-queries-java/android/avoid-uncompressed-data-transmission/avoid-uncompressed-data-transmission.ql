/**
 * @name Uncompressed Data Transmission
 * @description Transmitting a file over a network infrastructure without compressing it consumes more energy than with compression. Using GZIPOutputStream for HTTP requests reduces energy consumption by compressing data at least by 10% before transmission.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/uncompressed-data-transmission
 * @link https://green-code-initiative.org/rules#id:GCI504
 * @tags android
 * @tags java
 * @tags energy
 */

import java

from MethodCall mc
where
  mc.getMethod().getName() = "getOutputStream" and
  mc.getMethod().getDeclaringType().getAnAncestor().hasName("HttpURLConnection") and
  not exists(ConstructorCall gzipCtor |
    gzipCtor.getConstructor().getDeclaringType().hasName("GZIPOutputStream") and
    gzipCtor.getAnArgument() = mc
  ) and
  not exists(ConstructorCall outerCtor |
    outerCtor.getAnArgument*() = mc and
    exists(ConstructorCall gzipCtor |
      gzipCtor.getConstructor().getDeclaringType().hasName("GZIPOutputStream") and
      outerCtor.getAnArgument*() = gzipCtor
    )
  )
select mc,
  "Avoid using raw OutputStream for HTTP requests. Use GZIPOutputStream to compress data and reduce energy consumption."