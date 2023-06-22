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

    implementation(project(":domain"))

    //Spring
    implementation("org.springframework.boot:spring-boot-starter:3.1.0")
    //implementation("org.springframework.cloud:spring-cloud-aws-messaging:2.2.6.RELEASE")
    implementation("io.awspring.cloud:spring-cloud-aws-messaging:2.4.4")

    // Kotlin Dependencies
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")

    //AWS SQS
    implementation("software.amazon.awssdk:sqs:2.20.88")

    //Logging
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("net.logstash.logback:logstash-logback-encoder:7.3")
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
