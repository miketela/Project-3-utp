# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Jetpack Compose rules
-keep public class androidx.compose.runtime.snapshots.** { *; }
-keep public class androidx.compose.runtime.** { *; }
-keep public class androidx.compose.ui.** { *; }
-keep public class androidx.compose.animation.** { *; }
-keep public class androidx.compose.foundation.** { *; }
-keep public class androidx.compose.material.** { *; }
-keep public class androidx.compose.material3.** { *; }
-keep public class androidx.compose.ui.text.** { *; }
-keep public class androidx.compose.ui.graphics.** { *; }
-keep public class androidx.compose.ui.platform.** { *; }
-keep public class androidx.compose.ui.tooling.** { *; }
-keep public class androidx.compose.ui.viewinterop.** { *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile