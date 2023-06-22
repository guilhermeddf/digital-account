import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.7.22"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.7.1"
}


group = "com.dock.bank.digitalaccount"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven{
        url = uri("https://packages.confluent.io/maven")
    }
}


dependencies {

    implementation(project(":domain"))


    implementation("org.springframework.boot:spring-boot-starter:3.1.0")
    implementation("org.springframework.kafka:spring-kafka:3.0.7")

    // Kotlin Dependencies
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")


    implementation("io.confluent:kafka-schema-registry-client:5.3.0")
    implementation("org.apache.avro:avro:1.10.1")
    implementation("io.confluent:kafka-avro-serializer:5.3.0")
    implementation("com.sksamuel.avro4k:avro4k-core:0.41.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //Logging
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("net.logstash.logback:logstash-logback-encoder:7.3")
}

avro {
    templateDirectory.set("${project.projectDir}/src/main/resources/avro/")

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
