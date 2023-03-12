import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("kotlinx-serialization")
	id("io.spring.dependency-management")
	kotlin("multiplatform")
	kotlin("plugin.spring") version Dependencies.Versions.kotlin
	id("com.github.johnrengelman.shadow") version "7.1.0"
}
tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "18"
}
java {
	sourceCompatibility = JavaVersion.VERSION_17
}
tasks {
	withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
		archiveFileName.set("app.jar")
		archiveClassifier.set("")
		manifest {
			attributes["Main-Class"] = "com.example.backend.ApplicationKt"
		}
		configurations = listOf(project.configurations["runtimeClasspath"])
		minimize()
	}
}

//tasks {
//	val shadowJar by getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
//		archiveFileName.set("app.jar")
//		archiveClassifier.set("")
//		manifest {
//			attributes["Main-Class"] = "com.example.backend.ApplicationKt"
//		}
//		from(sourceSets["jvmBackMain"].output)
//		configurations = listOf(project.configurations["runtimeClasspath"])
//		minimize()
//	}
//}

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
		val jvmBackMain by getting {
			dependencies {
				with(Dependencies.MultiPlatform){
					implementation(kotlinxSerializationJson)
				}
				with(Dependencies.Spring.Defaults){
					implementation(actuator)
					implementation(web)
					implementation(dataJpa)
					implementation(mail)
					implementation(thymeleaf)
					implementation(migration)
					implementation(starterValidation)
					runtimeOnly(postgreSQLRun)
				}
				with(Dependencies.Spring.swagger){
					implementation(swaggerMVC)
				}
				with(Dependencies.Spring.ImageIO){
					implementation(bmp)
					implementation(tiff)
					implementation(jpeg)
					implementation(psd)
					implementation(pdf)
					implementation(hdr)
					implementation(servlet)
				}
				with(Dependencies.Spring){
					implementation(logging)
					implementation(skrapeIT)
					implementation(jackson)
					implementation(gson)
					implementation(jjwt)
					implementation(tomcat)
					implementation(guava)
					implementation(uniRest)
					implementation(commonsIO)
					implementation(commonsIO)
				}
				implementation(project(BuildModules.shared))
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
