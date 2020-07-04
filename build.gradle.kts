plugins {
  id("org.jetbrains.kotlin.jvm") version "1.3.72"
  `java-library`
  kotlin("kapt") version "1.3.72"
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
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
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
