# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\adt-bundle-windows-x86_64-20131030\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Preserve all fundamental application classes.
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep class com.xilada.xldutils.BaseApplication{*;}
-keep class com.xilada.xldutils.activitys.**{*;}
-keep class com.xilada.xldutils.adapter.**{*;}
-keep class com.xilada.xldutils.adapter.BaseRecyclerAdapter$ViewHolder{*;}
-keep class com.xilada.xldutils.fragment.**{*;}
-keep class com.xilada.xldutils.**{*;}
-keep class cn.sinata.util.**{*;}

#－----start-fresco----------－
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
#－----end-fresco----------－

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
# 正式项目需添加对应的java bean目录
-keep class com.xilada.xldutils.bean.** { *; }

##---------------End: proguard configuration for Gson  ----------
-keep class com.j256.ormlite.** { *; }
-dontwarn com.j256.ormlite.android.**
-dontwarn com.j256.ormlite.dao.**
-dontwarn com.j256.ormlite.db.**
-dontwarn com.j256.ormlite.field.**
-dontwarn com.j256.ormlite.logger.**
-dontwarn com.j256.ormlite.misc.**
-dontwarn com.j256.ormlite.stmt.**
-dontwarn com.j256.ormlite.support.**
-dontwarn com.j256.ormlite.table.**
