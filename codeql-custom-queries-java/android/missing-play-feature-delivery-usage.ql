/**
 * @name Missing Play Feature Delivery usage
 * @description Play Feature Delivery uses advanced app bundle functionality to enable conditional or on-demand distribution of certain features of your application. Hence, you can reduce the size of downloaded apps by using the android.dynamicFeatures property in the build.gradle file.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/missing-play-feature-delivery-usage
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class AndroidApplication extends Class {
  AndroidApplication() {
    this.getASupertype*().hasName("Application") and
    not this.hasName("Application") and
    not this.hasName("Context")
  }
}

class SplitInstallUsage extends MethodCall {
  SplitInstallUsage() {
    exists(Method m | m = this.getMethod() |
      m.getDeclaringType().hasName("SplitInstallManager") or
      m.getDeclaringType().hasName("SplitInstallManagerFactory") or
      m.getDeclaringType().hasName("SplitInstallRequest") or
      m.getDeclaringType().getName().matches("SplitInstallRequest%")
    )
  }
}

from AndroidApplication app
where
  not exists(SplitInstallUsage usage |
    usage.getEnclosingCallable().getDeclaringType() = app
  )
select app,
  "Application class " + app.getName() +
    " does not use Play Feature Delivery (SplitInstallManager). Consider using android.dynamicFeatures to reduce APK size for space-limited devices."
