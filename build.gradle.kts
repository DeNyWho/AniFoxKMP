buildscript {
    repositories {
        google()
        mavenCentral()
        maven(MultiplatformDependencies.gradleMaven)
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
    }
}

plugins {
    id(Plugins.ktLint) version Versions.ktLint
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(MultiplatformDependencies.composeMaven)
        maven(MultiplatformDependencies.jitpack)
    }
}

subprojects {
    apply(plugin = Plugins.ktLint)
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
