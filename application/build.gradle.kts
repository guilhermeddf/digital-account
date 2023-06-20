import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

extra["springBootVersion"] = "3.1.0"

plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    id("jacoco")
}

jacoco {
    toolVersion = "0.8.8"
}

group = "com.dock.bank.digitalaccount"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17



repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")
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