plugins {
    kotlin("jvm") version "1.9.20"
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
}

dependencies {
    val kotestVersion = "5.8.0"
    testImplementation(group = "io.kotest", name = "kotest-runner-junit5", version = kotestVersion)
    testImplementation(group = "io.kotest", name = "kotest-framework-datatest", version = kotestVersion)
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

    test {
        useJUnitPlatform()
    }
}
