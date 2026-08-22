# Keep Compose runtime
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }

# Keep Material3
-keep class androidx.compose.material3.** { *; }

# Keep lifecycle
-keep class androidx.lifecycle.** { *; }

# Keep coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep accessibility
-keep class android.view.accessibility.** { *; }

# Keep our app code
-keep class com.instantkey.android.** { *; }

# Keep Kotlin specific
-keepclassmembers class ** {
    kotlin.jvm.internal.* *;
}

# Keep data classes
-keepclassmembers class com.instantkey.android.data.** {
    *;
}

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

# Keep DataStore
-keep class androidx.datastore.** { *; }
