buildscript {
    repositories {
        google()
        mavenCentral()
        maven(Dependencies.MultiPlatform.gradleMaven)
        maven { url = uri("https://plugins.gradle.org/m2/") }
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Dependencies.Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Versions.kotlin}")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${Dependencies.Versions.spring}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Dependencies.Versions.kotlin}")
    }
}

plugins {
    id(Dependencies.Plugins.ktLint) version Dependencies.Versions.ktLint

}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(Dependencies.MultiPlatform.composeMaven)
        maven(Dependencies.MultiPlatform.gradleMaven)
        maven(Dependencies.MultiPlatform.jitpack)
    }
}

subprojects {
    apply(plugin = Dependencies.Plugins.ktLint)
    ktlint {
        debug.set(true)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        outputColorName.set("RED")
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}
