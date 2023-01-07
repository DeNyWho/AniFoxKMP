buildscript {
    repositories {
        google()
        mavenCentral()
        maven(MultiplatformDependencies.gradleMaven)
    }

    dependencies {
        classpath(Plugins.kotlin)
        classpath(Plugins.gradle)
        classpath(Plugins.kmpNativeCoroutines)
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

tasks.register("clean").configure {
    delete("build")
}
