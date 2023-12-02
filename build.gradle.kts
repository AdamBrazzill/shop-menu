import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "org.adambrazzill"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
dependencies {
    //testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    //testImplementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    //For Streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
}