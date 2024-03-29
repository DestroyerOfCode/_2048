/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.8.0" // Kotlin version to use
    id("org.jetbrains.compose") version "1.3.0"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven ("https://dl.bintray.com/qos-ch/maven")

}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation(libs.log4j.core)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito)
    testImplementation(libs.kotlin.test.junit5) {
        exclude(group = "org.junit.jupiter", module = "junit-jupiter-api")
        exclude(group = "org.junit.jupiter", module = "junit-jupiter-engine")
    }
    implementation(compose.desktop.currentOs)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

compose.desktop {
    application {
        mainClass = "_2048.MainKt"
    }
}

group = "main"
version = "1.0-SNAPSHOT"
description = "this is 2048 game"
java.sourceCompatibility = JavaVersion.VERSION_17


publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}