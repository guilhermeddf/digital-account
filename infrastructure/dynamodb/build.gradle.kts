import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

extra["awsVersion"] = "1.12.470"

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

    //Project
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter:3.1.0")

    //Database
    implementation("com.amazonaws:aws-java-sdk-dynamodb:${property("awsVersion")}")
    implementation(project(mapOf("path" to ":infrastructure:postgres")))

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