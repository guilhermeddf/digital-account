import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

extra["springBootVersion"] = "3.1.0"

plugins {
    kotlin("jvm") version "1.7.22"

}

group = "com.dock.bank.digitalaccount"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {

    implementation(project(":domain"))

    //Spring
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")

    //Database
    implementation("org.postgresql:postgresql:42.3.8")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${property("springBootVersion")}")

    //Security
    implementation("org.springframework.boot:spring-boot-starter-security:${property("springBootVersion")}")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("com.auth0:java-jwt:4.4.0")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}