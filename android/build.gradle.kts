plugins {
    id(Dependencies.Plugins.androidApplication)
    kotlin(Dependencies.Plugins.android)
}

android {
    compileSdk = Dependencies.Android.compileSdkVersion

    defaultConfig {
        applicationId = Dependencies.Android.id

        minSdk = Dependencies.Android.minSdkVersion
        targetSdk = Dependencies.Android.targetSdkVersion
        versionCode = Dependencies.Android.versionCode
        versionName = Dependencies.Android.versionName

        testInstrumentationRunner = Dependencies.Android.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.complierCompose
    }
}

dependencies {
    implementation(project(BuildModules.shared))
    with(Dependencies.Android) {
        implementation(androidCore)
        implementation("androidx.compose.runtime:runtime:1.0.1")
        implementation("androidx.compose.material:material-icons-extended:")
        implementation(material)
        implementation(composeUI)
        implementation(icons)
        implementation(composeMaterial)
        implementation(composeTooling)
        implementation(composeRuntime)
        implementation(composeRuntimeLive)
        implementation(composeUtil)
        implementation(composeActivity)
        implementation(accompanistPager)
        implementation(accompanistInsets)
        implementation(accompanistAnimation)
        implementation(accompanistSystemUIController)
        implementation(accompanistMaterialPlaceHolder)
        implementation(accompanistPagerIndicator)
        implementation(lifecycleRuntime)
        implementation(koinAndroid)
        implementation(koinCompose)
        implementation(composeNavigation)
        implementation(coil)
        implementation(palette)
        implementation(ratingBar)
        implementation(oneBoneToolbar)
        debugImplementation(leakCanary)
        implementation(osmdroidAndroid)
    }


}
