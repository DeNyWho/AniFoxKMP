plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
}

android {
    compileSdk = Android.compileSdkVersion

    defaultConfig {
        applicationId = Android.id

        minSdk = Android.minSdkVersion
        targetSdk = Android.targetSdkVersion
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = Android.testInstrumentationRunner
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
        kotlinCompilerExtensionVersion = Versions.complierCompose
    }
}

dependencies {
    implementation(project(BuildModules.shared))

    implementation(Android.androidCore)

    implementation(Android.material)

    implementation(Android.composeUI)
    implementation(Android.composeMaterial)
    implementation(Android.composeTooling)
    implementation(Android.composeRuntime)
    implementation(Android.composeUtil)
    implementation(Android.composeActivity)

    implementation(Android.accompanistPager)
    implementation(Android.accompanistInsets)
    implementation(Android.accompanistAnimation)
    implementation(Android.accompanistSystemUIController)
    implementation(Android.accompanistMaterialPlaceHolder)
    implementation(Android.accompanistPagerIndicator)

    implementation(Android.lifecycleRuntime)

    // Koin-Dependency injection
    implementation(Android.koinAndroid)
    implementation(Android.koinCompose)

    // Compose Navigation-Navigation between various screens
    implementation(Android.composeNavigation)

    // Coil-Image Loader
    implementation(Android.coil)

    // Palette-Used to extract color palette from images
    implementation(Android.palette)

    // Gowtham Compose Rating Bar
    implementation(Android.ratingBar)

    // Collapsing toolbar
    implementation(Android.oneBoneToolbar)
    implementation("androidx.compose.material:material:1.1.1")

    // Leak Canary - Memory leaks
    debugImplementation(Android.leakCanary)
}
