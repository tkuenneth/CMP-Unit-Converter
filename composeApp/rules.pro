-keep class androidx.datastore.*.** {*;}
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class kotlinx.serialization.** { *; }
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }
-keep class * extends androidx.room.RoomDatabase { <init>(); }

-dontnote kotlinx.serialization.AnnotationsKt
-dontnote kotlinx.serialization.SerializationKt

-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepattributes *Annotation*, InnerClasses

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

-adaptresourcefilenames okhttp3/internal/publicsuffix/PublicSuffixDatabase.gz

-dontwarn kotlinx.coroutines.debug.*
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-dontwarn org.slf4j.**
-dontwarn javax.naming.**

-dontobfuscate
-ignorewarnings
