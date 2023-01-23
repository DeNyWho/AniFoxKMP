object Dependencies {

    object Versions {

        // Project Level Plugins
        const val kotlin = "1.7.20"
        const val gradle = "7.3.0"
        const val ktLint = "10.3.0"
        const val spring = "3.0.2"
        const val springDep = "1.1.0"

        // KMM Dependencies Version
        const val kmpNativeCoroutines = "0.13.3"
        const val ktor = "2.2.2"
        const val kotlinxCoroutines = "1.6.4"
        const val koin = "3.2.2"
        const val kotlinxSerialization = "1.7.20"
        const val multiplatformSettings = "0.8.1"

        // Backend Dependencies Version
        const val swagger = "1.6.9"
        const val skrapeIT = "1.2.2"
        const val gson = "2.9.0"
        const val springLogging = "2.1.23"
        const val jjwt = "0.9.1"
        const val jjwtApi = "0.10.6"
        const val uniRest = "1.4.9"
        const val guava = "31.1-jre"
        const val mapStruct = "1.5.2.Final"
        const val common = "2.4"
        const val tomcat = "9.0.56"

        // Desktop Dependencies Version
        const val composeDesktop = "1.2.1"

        // Android Dependencies
        const val complierCompose = "1.3.2"
        const val compose = "1.2.1"
        const val androidCore = "1.7.0"
        const val runtime = "1.0.1"
        const val material = "1.6.0"
        const val appCompat = "1.4.1"
        const val splash = "1.0.0"
        const val constraintLayout = "2.1.4"
        const val composeMat3 = "1.0.0-alpha13"
        const val compose_activity = "1.4.0"
        const val compose_constraint = "1.0.0-rc01"
        const val accompanist = "0.25.1"
        const val navigation = "2.5.2"
        const val lifecycle = "2.3.1"
        const val coil = "1.4.0"
        const val palette = "28.0.0"
        const val paging = "3.0.0"
        const val coroutines = "1.5.2"
        const val firebase_bom = "30.3.1"
        const val rating_bar = "1.1.1"
        const val leakCanary = "2.9.1"
        const val paging_compose = "1.0.0-alpha14"
        const val oneBoneToolbar="2.3.4"
        const val osmdroidAndroid = "6.1.10"

    }

    object Plugins {
        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
        const val kotlinter = "org.jmailen.kotlinter"
        const val kapt = "kotlin-kapt"
        const val navigationSafeArgs = "androidx.navigation.safeargs"
        const val nativeCoroutines = "com.rickclephas.kmp.nativecoroutines"
        const val kotlinXSerialization = "plugin.serialization"
        const val dagger = "dagger.hilt.android.plugin"
        const val ktLint = "org.jlleitschuh.gradle.ktlint"
        const val kotlinPlugin = "gradle-plugin"
        const val multiplatform = "multiplatform"
        const val android = "android"
    }

    object MultiPlatform {
        const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"

        const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.runtime}"

        const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"

        const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kmpNativeCoroutines = "com.rickclephas.kmp:kmp-nativecoroutines-gradle-plugin:${Versions.kmpNativeCoroutines}"


        const val multiplatformSettings = "com.russhwolf:multiplatform-settings-no-arg:${Versions.multiplatformSettings}"
        const val multiplatformSettingsCoroutines = "com.russhwolf:multiplatform-settings-coroutines:${Versions.multiplatformSettings}"

        const val gradleMaven = "https://plugins.gradle.org/m2/"
        const val jitpack = "https://jitpack.io"
        const val composeMaven = "https://maven.pkg.jetbrains.space/public/p/compose/dev"
    }

    object Ktor {
        const val serverCore = "io.ktor:ktor-server-core:${Versions.ktor}"
        const val serverNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
        const val serverCors = "io.ktor:ktor-server-cors:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val json = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"

        const val serverContentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktor}"

        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val clientJava = "io.ktor:ktor-client-java:${Versions.ktor}"
        const val clientDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
        const val clientJs = "io.ktor:ktor-client-js:${Versions.ktor}"
    }

    object Spring {

        const val logging = "io.github.microutils:kotlin-logging-jvm:${Versions.springLogging}"
        const val skrapeIT = "it.skrape:skrapeit:${Versions.skrapeIT}"
        const val jackson = "com.fasterxml.jackson.module:jackson-module-kotlin"
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val jjwt = "io.jsonwebtoken:jjwt:${Versions.jjwt}"
        const val jjwtApi = "io.jsonwebtoken:jjwt-api:${Versions.jjwtApi}"
        const val uniRest = "com.mashape.unirest:unirest-java:${Versions.uniRest}"
        const val tomcat = "org.apache.tomcat.embed:tomcat-embed-core:${Versions.tomcat}"
        const val guava = "com.google.guava:guava:${Versions.guava}"
        const val mapStruct = "org.mapstruct:mapstruct:${Versions.mapStruct}"
        const val commonsIO = "commons-io:commons-io:${Versions.common}"

        object Defaults {
            const val actuator = "org.springframework.boot:spring-boot-starter-actuator:${Versions.spring}"
            const val web = "org.springframework.boot:spring-boot-starter-web:${Versions.spring}"
            const val dataJpa = "org.springframework.boot:spring-boot-starter-data-jpa:${Versions.spring}"
            const val mail = "org.springframework.boot:spring-boot-starter-mail:${Versions.spring}"
            const val security = "org.springframework.boot:spring-boot-starter-security:${Versions.spring}"
            const val thymeleaf = "org.springframework.boot:spring-boot-starter-thymeleaf:${Versions.spring}"
        }

        const val scrapeIT = "it.skrape:skrapeit:"

        object swagger {
            const val swaggerData = "org.springdoc:springdoc-openapi-data-rest:${Versions.swagger}"
            const val swaggerUi = "org.springdoc:springdoc-openapi-ui:${Versions.swagger}"
            const val swaggerKotlin = "org.springdoc:springdoc-openapi-kotlin:${Versions.swagger}"
        }
    }


    object Android {

        object Defaults {

            const val id = "com.example.anifox"
            const val appName = "AniFox"

            const val versionName="demo"
            const val versionCode=1

            const val buildToolVersion = "30.0.3"

            const val minSdkVersion = 21
            const val compileSdkVersion = 33
            const val targetSdkVersion = compileSdkVersion

        }

        object AndroidXAnDCompose {

            const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            const val androidCore = "androidx.core:core-ktx:${Versions.androidCore}"
            const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
            const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
            const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
            const val composeTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
            const val composeRuntimeLive =
                "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
            const val composeUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
            const val composeConstraintLayout =
                "androidx.constraintlayout:constraintlayout-compose:${Versions.compose_constraint}"
            const val composeActivity =
                "androidx.activity:activity-compose:${Versions.compose_activity}"
            const val material3 = "androidx.compose.material3:material3:${Versions.composeMat3}"
            const val composeNavigation =
                "androidx.navigation:navigation-compose:${Versions.navigation}"
            const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val icons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
            const val oneBoneToolbar = "me.onebone:toolbar-compose:${Versions.oneBoneToolbar}"
            const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

        }

        object Accompanist {

            const val accompanistPager =
                "com.google.accompanist:accompanist-pager:${Versions.accompanist}"
            const val accompanistInsets =
                "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
            const val accompanistAnimation =
                "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"
            const val accompanistSystemUIController =
                "com.google.accompanist:accompanist-systemuicontroller:0.17.0"
            const val accompanistMaterialPlaceHolder =
                "com.google.accompanist:accompanist-placeholder-material:${Versions.accompanist}"
            const val accompanistPagerIndicator =
                "com.google.accompanist:accompanist-pager-indicators:0.22.0-rc"

        }

        object Koin {

            const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
            const val koinCompose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"

        }

        const val osmdroidAndroid = "org.osmdroid:osmdroid-android:${Versions.osmdroidAndroid}"

        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

        const val material = "com.google.android.material:material:${Versions.material}"

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    }

}