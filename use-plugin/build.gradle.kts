plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
    application
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    implementation("io.mattmoore.kotlin.annotation:annotation-processor:0.1.0-SNAPSHOT")
    kapt("io.mattmoore.kotlin.annotation:annotation-processor:0.1.0-SNAPSHOT")
}

application {
    mainClassName = "io.mattmoore.kotlin.annotation.example.AppKt"
}
