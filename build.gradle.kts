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
    // Source: https://mvnrepository.com/artifact/org.xhtmlrenderer/flying-saucer-core 
    implementation("org.xhtmlrenderer:flying-saucer-core:10.0.6")
    // Source: https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
	// // Source: https://mvnrepository.com/artifact/com.microsoft.playwright/playwright
	// implementation("com.microsoft.playwright:playwright:1.58.0")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	exclude("**/driver/mac/**")
    exclude("**/driver/win/**")
}

// tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
// 	// 1. Set the builder
//     // builder.set("paketobuildpacks/builder-jammy-buildpackless-tiny")
    
//     // // 2. EXPLICITLY add the buildpacks. 
//     // // Tiny builders need to be told exactly what to run.
//     // buildpacks.set(listOf(
//     //     "docker.io/paketobuildpacks/oracle",
//     //     "docker.io/paketobuildpacks/java-native-image"
//     // ))

//     // 3. Environment settings
//     imagePlatform.set("linux/amd64")
//     environment.put("BP_NATIVE_IMAGE", "true")
//     environment.put("BP_JVM_VERSION", "25")
// }