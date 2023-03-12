plugins {
    id(Dependencies.Plugins.androidApplication)
    kotlin(Dependencies.Plugins.android)
}

android {
    with(Dependencies.Android.Defaults) {
        compileSdk = compileSdkVersion

        defaultConfig {
            applicationId = id

            minSdk = minSdkVersion
            targetSdk = targetSdkVersion
            versionCode = versionCode
            versionName = versionName

            testInstrumentationRunner = testInstrumentationRunner
        }
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
    implementation(Dependencies.MultiPlatform.composeRuntime)
    with(Dependencies.Android.AndroidXAnDCompose) {
        implementation(pagingRuntime)
        implementation(pagingCompose)
        implementation(viewPager)
        implementation(pagingCommon)
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
    with(Dependencies.Android) {
        implementation(material)
        implementation(osmdroidAndroid)
        debugImplementation(leakCanary)
    }
    with(Dependencies.Android.UI){
        implementation(shimmer)
    }
    with(Dependencies.Android.Koin) {
        implementation(koinAndroid)
        implementation(koinCompose)
    }
    with(Dependencies.Android.Accompanist) {
        implementation(accompanistPager)
        implementation(accompanistInsets)
        implementation(accompanistAnimation)
        implementation(accompanistSystemUIController)
        implementation(accompanistMaterialPlaceHolder)
        implementation(accompanistPagerIndicator)
    }


}
