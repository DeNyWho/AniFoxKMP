import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("kotlinx-serialization")
	id("io.spring.dependency-management")
	kotlin("multiplatform")
	kotlin("plugin.spring") version Dependencies.Versions.kotlin
	id("com.github.johnrengelman.shadow") version "7.1.0"
}

springBoot {
	mainClass.set("com.example.backend.ApplicationKt")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	google()
	mavenCentral()
	gradlePluginPortal()
	maven(Dependencies.MultiPlatform.composeMaven)
	maven(Dependencies.MultiPlatform.gradleMaven)
	maven(Dependencies.MultiPlatform.jitpack)
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
				with(Dependencies.Spring.KeyCloak) {
					implementation(keycloakAdminClient)
					implementation(keycloakSpring)
				}
				with(Dependencies.Spring.Cache) {
					implementation(ehcache)
					implementation(javaxCache)
				}
				with(Dependencies.Spring.Oauth) {
					implementation(securityConf)
					implementation(securityOauthCore)
					implementation(securityOauthClient)
					implementation(securityOauthAutoConfigure)
					implementation(jjwtApi)
					runtimeOnly(jjwtImpl)
					runtimeOnly(jjwtJackson)
					implementation(web)
				}
				with(Dependencies.Ktor) {
					implementation(clientCore)
					implementation(clientJava)
					implementation(clientLogging)
					implementation(clientJson)
					implementation(json)
					implementation(contentNegotiation)
				}
				with(Dependencies.MultiPlatform) {
					implementation(kotlinxSerializationJson)
				}
				with(Dependencies.Spring.Defaults) {
					implementation(actuator)
					implementation(web)
					implementation(dataJpa)
					implementation(mail)
					implementation(thymeleaf)
					implementation(migration)
					implementation(starterValidation)
					implementation(security)
					implementation(cache)
					implementation(jwt)
					implementation(mail)
					implementation(webSpr)
					implementation(validation)
					implementation(springCore)
					runtimeOnly(postgreSQLRun)
				}
				with(Dependencies.Spring.swagger) {
					implementation(swaggerMVC)
				}
				with(Dependencies.Spring.ImageIO) {
					implementation(bmp)
					implementation(tiff)
					implementation(jpeg)
					implementation(psd)
					implementation(pdf)
					implementation(hdr)
					implementation(servlet)
				}
				with(Dependencies.Spring) {
					implementation(logging)
					implementation(skrapeIT)
					implementation(jackson)
					implementation(gson)
					implementation(tomcat)
					implementation(guava)
					implementation(uniRest)
					implementation(commonsIO)
					implementation(commonsText)
					implementation(javax)
					implementation(jakarta)
					implementation(hibernate)
				}
			}
		}
	}
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
