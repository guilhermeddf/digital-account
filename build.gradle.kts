import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

extra["testcontainersVersion"] = "1.18.1"
extra["springBootVersion"] = "3.1.0"
extra["restAssured"] = "5.3.0"
extra["restAssuredDep"] = "5.2.1"
extra["awsVersion"] = "1.12.470"
extra["cucumberVersion"] = "7.12.1"

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
	id("org.sonarqube") version "3.5.0.2730"
	id("jacoco")
}

sonarqube {
	properties {
		property ("sonar.projectKey", "guilhermeddf_digital-account")
		property ("sonar.organization", "guilhermeddf")
		property ("sonar.host.url", "https://sonarcloud.io")
	}
}

subprojects {
	sonarqube {
		properties {
			property ("sonar.projectKey", "guilhermeddf_digital-account")
			property ("sonar.organization", "guilhermeddf")
			property ("sonar.host.url", "https://sonarcloud.io")
			property("sonar.junit.reportPaths","${project.projectDir}/build/reports/jacoco/test/")
		}
	}
}

jacoco {
	toolVersion = "0.8.8"
}

group = "com.dock.bank"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {

	// Spring Dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:${property("springBootVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${property("springBootVersion")}")

	//Security
	implementation("org.springframework.boot:spring-boot-starter-security:${property("springBootVersion")}")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("com.auth0:java-jwt:4.4.0")

	//Metrics
	implementation("org.springframework.boot:spring-boot-starter-actuator:${property("springBootVersion")}")
	implementation("io.micrometer:micrometer-registry-prometheus:1.11.0")

	//CircuitBreaker
	//implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:3.0.2")

	//Documentation
	implementation("org.springdoc:springdoc-openapi-ui:1.6.6")

	// Kotlin Dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	//Logging
	implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
	implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
	implementation("net.logstash.logback:logstash-logback-encoder:7.3")

	//Database
	implementation("org.postgresql:postgresql:42.3.8")
	implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.1")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.2")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")

	//Redis
	//implementation("org.springframework.boot:spring-boot-starter-data-redis:${property("springBootVersion")}")
	//implementation("redis.clients:jedis:4.4.0")

	//AWS
	//implementation("org.springframework.cloud:spring-cloud-starter-aws-messaging:2.2.6.RELEASE")
	implementation("com.amazonaws:aws-java-sdk-secretsmanager:${property("awsVersion")}")
	//implementation("com.amazonaws:aws-java-sdk-sqs:${property("awsVersion")}")
	//implementation("com.amazonaws:aws-java-sdk-dynamodb:${property("awsVersion")}")

	//implementation("org.springframework.kafka:spring-kafka:3.0.7")

	testImplementation("org.springframework.boot:spring-boot-starter-test:${property("springBootVersion")}")
	testImplementation("io.mockk:mockk:1.12.0")

	testImplementation("ch.qos.logback:logback-classic:1.4.7")

	// Test Container
	testImplementation("org.testcontainers:testcontainers:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:postgresql:${property("testcontainersVersion")}")
	testImplementation("org.testcontainers:junit-jupiter")

	// Rest Assured
	testImplementation("io.rest-assured:rest-assured:${property("restAssured")}")
	testImplementation ("io.rest-assured:json-path:${property("restAssuredDep")}")
	testImplementation ("io.rest-assured:xml-path:${property("restAssuredDep")}")

	//Cucumber
	testImplementation("io.cucumber:cucumber-java:${property("cucumberVersion")}")
	testImplementation("io.cucumber:cucumber-junit:${property("cucumberVersion")}")
	testImplementation("io.cucumber:cucumber-spring:${property("cucumberVersion")}")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<JacocoReport> {
	reports {
		xml.required.set(true)
		html.required.set(false)
		csv.required.set(false)
	}
	dependsOn(tasks.test)
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}
