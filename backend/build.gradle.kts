import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("multiplatform")
	kotlin("plugin.spring") version Dependencies.Versions.kotlin
}

repositories {
	mavenCentral()
}

kotlin {
	jvm("jvmBack") {
		compilations.all {
			kotlinOptions.jvmTarget = "18"
		}
		withJava()
	}
	sourceSets {
		println(this.names)
		val jvmBackMain by getting {
			dependencies {
				implementation(project(":shared"))
			}
		}
	}
}

springBoot {
	mainClass.set("com.example.backend.ApplicationKt")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "18"
	}
	kotlinOptions.jvmTarget = "18"
}

tasks.withType<Test> {
	useJUnitPlatform()
}
