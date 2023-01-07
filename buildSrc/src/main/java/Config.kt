object Config {
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"

    object Dependencies {
        const val kotlinPlugin = "gradle-plugin"
        const val androidPlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    }

    object Repositories {
        const val gradleMaven = "https://plugins.gradle.org/m2/"
        const val jitpack = "https://jitpack.io"
    }

    object Plugins {
        const val android = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
        const val kotlinter = "org.jmailen.kotlinter"
        const val kapt = "kotlin-kapt"
        const val navigationSafeArgs = "androidx.navigation.safeargs"
        const val serialization = "kotlinx-serialization"
        const val dagger = "dagger.hilt.android.plugin"
    }

}