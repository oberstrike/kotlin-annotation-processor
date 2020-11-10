plugins {
  id("org.jetbrains.kotlin.jvm") version "1.4.10"
  `java-library`
  kotlin("kapt") version "1.4.10"
  `maven-publish`
}

group = "io.mattmoore.kotlin.annotation"
version = "0.1.0-SNAPSHOT"

repositories {
  jcenter()
}

dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.google.auto.service:auto-service:1.0-rc4")
  kapt("com.google.auto.service:auto-service:1.0-rc4")
  implementation("com.squareup:kotlinpoet:1.6.0")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")

  implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.2.0")
  implementation("com.squareup:kotlinpoet:1.7.1")
  implementation("com.squareup:kotlinpoet-metadata:1.7.1")
  implementation("com.squareup:kotlinpoet-metadata-specs:1.7.1")
  implementation("com.squareup:kotlinpoet-classinspector-elements:1.7.1")
  implementation("com.squareup:kotlinpoet-classinspector-reflective:1.7.1")
  implementation("org.eclipse.microprofile.openapi:microprofile-openapi-api:2.0-RC3")
  implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")

}

java {
  withSourcesJar()
  withJavadocJar()
}

publishing {
  publications {
    create<MavenPublication>("annotation-processor") {
      from(components["java"])
      groupId = "io.mattmoore.kotlin.annotation"
      artifactId = "annotation-processor"
      version = version
    }
  }
}
