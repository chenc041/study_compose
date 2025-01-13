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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# 保留所有用 @InstallIn, @Module, @HiltAndroidApp 等 Hilt 注解标记的类
-keepclasseswithmembers class * {
    @dagger.hilt.android.lifecycle.* <init>(...);
}
-keepattributes RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,RuntimeVisibleParameterAnnotations,RuntimeInvisibleParameterAnnotations,AnnotationDefault

# 保留所有带 @Inject, @Provides, @IntoSet, @IntoMap, @ multibindings 等注解的方法和字段
-keepclassmembers class * {
    @javax.inject.* *;
    @dagger.* *;
}

# 保留所有 dagger 内部生成的类
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class dagger.internal.* { *; }
-keep class dagger.producers.* { *; }

# 保留所有的接口以及它们的实现，因为它们可能用于组件绑定
-keep interface * extends * { *; }
-keep class * implements * { *; }

# 如果你使用了 AndroidX Hilt，也需要保持相关类
-keep class androidx.hilt.** { *; }
-keep interface androidx.hilt.** { *; }

# 如果你有其他库也使用 Dagger/Hilt，确保添加对应的规则来保护这些库的类。