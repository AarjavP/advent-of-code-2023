plugins {
    kotlin("jvm") version "1.9.20"
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(group = "io.kotest", name = "kotest-assertions-core-jvm", version = "5.8.0")
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

    test {
        useJUnitPlatform()
    }
}
