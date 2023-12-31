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

# Keep Retrofit classes
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep GSON classes
-keep class com.google.gson.** { *; }
-keepattributes Signature

# If you are using OkHttp as the HTTP client, keep OkHttp classes
-keep class okhttp3.** { *; }

# Keep model classes (change your_package_name with the actual package name)
-keep class com.arihant.edurite.models.** { *; }

# Keep any other classes and methods you need to retain
-keep class com.arihant.edurite.Retrofit.** { *; }