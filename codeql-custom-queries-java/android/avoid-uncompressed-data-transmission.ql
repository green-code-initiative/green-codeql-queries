import java

from MethodCall mc
where
  mc.getMethod().getName() = "getOutputStream" and
  not exists(
    // Vérifie si le OutputStream est utilisé dans un appel de constructeur GZIPOutputStream
    ConstructorCall gzipCtor |
      gzipCtor.getConstructor().getDeclaringType().getName() = "GZIPOutputStream" and
      gzipCtor.getAnArgument() = mc
  ) and
  not exists(
    // Vérifie si le OutputStream est utilisé dans une méthode write()
    MethodCall writeCall |
      writeCall.getMethod().getName() = "write" and
      writeCall.getAnArgument() instanceof Literal and
      exists(Expr parent | parent = mc.getParent() and parent = writeCall)
  )
select mc,
  "Avoid using raw OutputStream for HTTP requests. Use GZIPOutputStream to compress data and reduce energy consumption."