plugins {
    kotlin(Plugins.multiplatform)
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.compose
}

group = "com.example"
version = "1.0-SNAPSHOT"

android {
    compileSdk = Android.compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Android.minSdkVersion
        targetSdk = Android.targetSdkVersion
    }
}

kotlin {
    android()
    jvm("jvmMain") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    sourceSets {
        sourceSets["commonMain"].dependencies {
            implementation(MultiplatformDependencies.kotlinxCoroutines)

//            implementation(MultiplatformDependencies.kotlinxSerialization)

            api(MultiplatformDependencies.koinCore)

            implementation(MultiplatformDependencies.ktorCore)
            implementation(MultiplatformDependencies.ktorSerialization)
            implementation(MultiplatformDependencies.ktorLogging)

            implementation(MultiplatformDependencies.multiplatformSettings)
            implementation(MultiplatformDependencies.multiplatformSettingsCoroutines)
        }

        sourceSets["androidMain"].dependencies {
            implementation(MultiplatformDependencies.ktorAndroid)
        }

//        sourceSets["jvmMain"].dependencies {
//            api(MultiplatformDependencies.ktorJvm)
//        }
    }
}