import org.jetbrains.kotlin.gradle.tasks.KotlinCompile



plugins {
    kotlin("jvm") version "1.7.22"
}

apply("avro-gradle-plugin")

group = "com.dock.bank.digitalaccount"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

//compileAvro.source = project.projectDir/src/main/resources/avro

dependencies {

    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter:3.1.0")
    // Kotlin Dependencies
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")

    //Kafka
    implementation("org.springframework.kafka:spring-kafka:3.0.7")

    implementation("org.apache.avro:avro:1.11.1")
    implementation("org.apache.avro:avro-compiler:1.11.1")
    implementation("org.apache.maven:maven-artifact:3.9.2")
    //implementation("io.confluent:kafka-avro-serializer:5.3.0")
    implementation("org.apache.avro:avro-gradle-plugin:1.7.2")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")

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
