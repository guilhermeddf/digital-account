import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
	id("org.sonarqube") version "4.0.0.2929"
}

sonarqube {
	properties {
		property("sonar.projectKey", "digital-account")
		property("sonar.organization", "my-organization")
		property("sonar.projectName", "digital-account")
		property("sonar.sources", "src")
	}
}

group = "com.dock.bank"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

extra["testcontainersVersion"] = "1.18.1"
extra["springBootVersion"] = "3.0.6"

	repositories {
	mavenCentral()
}

dependencies {

	// Spring Dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:${property("springBootVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${property("springBootVersion")}")
	implementation("org.springframework.cloud:spring-cloud-starter-aws-messaging:2.2.6.RELEASE")
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

	// Kotlin Dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.apache.logging.log4j:log4j-api:2.17.1")
	implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.1")


	// Database Dependencies
	implementation("org.postgresql:postgresql:42.3.8")

	// Jackson Dependencies
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")

	//implementation("org.springframework.data:spring-data-redis:3.1.0")
	//implementation("redis.clients:jedis:4.4.0")

	// AWS Dependencies
	implementation("com.amazonaws:aws-java-sdk-secretsmanager:1.12.468")
	implementation("com.amazonaws:aws-java-sdk-sqs:1.12.467")
	implementation("com.amazonaws:aws-java-sdk-dynamodb:1.12.468")

	// Test Dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.12.0")

	testImplementation("org.testcontainers:testcontainers:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:postgresql:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:junit-jupiter")

	testImplementation("io.rest-assured:rest-assured:4.5.1")
	testImplementation ("io.rest-assured:json-path:4.5.1")
	testImplementation ("io.rest-assured:xml-path:4.5.1")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
