plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id ("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

group = "br.com.caju"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val postgresqlversion = "42.7.3"
 val mockk = "1.10.0"
val wiremockVersion = "2.27.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// Database dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql:$postgresqlversion")
	implementation("org.flywaydb:flyway-database-postgresql")

	testImplementation("org.springframework:spring-test:5.3.25")

	implementation( "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1" )

	testImplementation("com.ninja-squad:springmockk:1.1.3")
	testImplementation("org.amshove.kluent:kluent:1.65")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("com.github.tomakehurst:wiremock-standalone:$wiremockVersion")
	testImplementation("org.mock-server:mockserver-junit-jupiter:5.8.0")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
