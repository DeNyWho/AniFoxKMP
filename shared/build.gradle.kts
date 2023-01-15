plugins {
    kotlin(Dependencies.Plugins.multiplatform)
    id("com.android.library")
    id("org.jetbrains.compose") version Dependencies.Versions.compose
}

group = "com.example"
version = "1.0-SNAPSHOT"

android {
    compileSdk = Dependencies.Android.compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Dependencies.Android.minSdkVersion
        targetSdk = Dependencies.Android.targetSdkVersion
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
        val commonMain by getting {
            dependencies {
                with(Dependencies.Ktor) {
                    implementation(clientCore)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(contentNegotiation)
                    implementation(json)
                }

                implementation(Dependencies.MultiPlatform.multiplatformSettings)
                implementation(Dependencies.MultiPlatform.multiplatformSettingsCoroutines)

                api(Dependencies.MultiPlatform.koinCore)
                implementation(Dependencies.MultiPlatform.kotlinxCoroutines)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.clientAndroid)
                implementation("androidx.compose.runtime:runtime:1.0.1")
            }
        }
    }
}