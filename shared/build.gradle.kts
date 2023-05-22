plugins {
    kotlin(Dependencies.Plugins.multiplatform)
    id("com.android.library")
    id("kotlinx-serialization")
    id("org.jetbrains.compose") version Dependencies.Versions.compose
}

group = "com.example"
version = "1.0-SNAPSHOT"

android {
    compileSdk = Dependencies.Android.Defaults.compileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Dependencies.Android.Defaults.minSdkVersion
        targetSdk = Dependencies.Android.Defaults.targetSdkVersion
    }
    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE")
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
                    implementation(clientApache)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(contentNegotiation)
                    implementation(json)
                }
                api(compose.runtime)
                implementation(Dependencies.MultiPlatform.multiplatformSettings)
                implementation(Dependencies.MultiPlatform.multiplatformSettingsCoroutines)

                api(Dependencies.MultiPlatform.koinCore)
                implementation(Dependencies.MultiPlatform.kotlinxCoroutines)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.clientAndroid)
                with(Dependencies.Android.AndroidXAnDCompose) {
                    implementation(pagingRuntime)
                    implementation(pagingCompose)
                    implementation(androidCore)
                    implementation(composeUI)
                    implementation(icons)
                    implementation(composeMaterial)
                    implementation(composeTooling)
                    implementation(composeRuntimeLive)
                    implementation(composeUtil)
                    implementation(composeActivity)
                    implementation(lifecycleRuntime)
                    implementation(composeNavigation)
                    implementation(coil)
                    implementation(oneBoneToolbar)
                }
            }
        }
    }
}