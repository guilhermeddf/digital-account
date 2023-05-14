import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
}

group = "com.dock.bank"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

extra["testcontainersVersion"] = "1.15.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.apache.logging.log4j:log4j-api:2.17.1")
	implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.1")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.6")

	implementation("org.postgresql:postgresql:42.3.3")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")



	implementation("org.springframework.cloud:spring-cloud-starter-aws-messaging:2.2.6.RELEASE")
	//implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

	implementation("com.amazonaws:aws-java-sdk-secretsmanager:1.12.468")
	implementation("com.amazonaws:aws-java-sdk-sqs:1.12.467")
	implementation("com.amazonaws:aws-java-sdk-dynamodb:1.12.468")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.12.0")

	testImplementation("org.testcontainers:testcontainers:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:postgresql:1.16.3")
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
