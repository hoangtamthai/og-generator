plugins {
	java
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.graalvm.buildtools.native") version "0.11.4"
}

group = "de.tamthai"
version = "0.0.1-SNAPSHOT"
description = "og image generator service"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	
	// Source: https://mvnrepository.com/artifact/com.microsoft.playwright/playwright
	implementation("com.microsoft.playwright:playwright:1.58.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
