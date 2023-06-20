rootProject.name = "digital-account"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include("application")
include("domain")

include("launcher")

include("infrastructure:postgres")
include("infrastructure:dynamodb")
include("infrastructure:kafka-producer")