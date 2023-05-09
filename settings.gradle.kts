pluginManagement {
    repositories {
        mavenCentral()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }

    plugins {
        val loom_version: String by settings
        val kotlin_version: String by settings

        id("fabric-loom") version loom_version
        kotlin("jvm") version kotlin_version
    }
}