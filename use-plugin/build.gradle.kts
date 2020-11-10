plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    kotlin("kapt") version "1.4.10"
    application
}

repositories {
    mavenLocal()
    jcenter()
    maven("https://dl.bintray.com/blipinsk/maven/")
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    implementation(project(":compiler-plugin"))
    kapt(project(":compiler-plugin"))
    implementation ("com.bartoszlipinski:data-class-builder:0.1.0")
    kapt("com.bartoszlipinski:data-class-builder-compiler:0.1.0")

}

application {
    mainClassName = "io.mattmoore.kotlin.plugins.annotations.example.AppKt"
}
