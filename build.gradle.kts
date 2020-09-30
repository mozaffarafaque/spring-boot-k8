buildscript {
    repositories {
        mavenCentral()
    }
}

description = "Test spring boot application"

plugins {
    id("java")
    id("idea")
    id("application")
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.4.0"
}

allprojects {
    repositories {
        mavenCentral()

        jcenter()
    }
}

subprojects {
    group = "com.mozafaq.test.springboot"
    version = "1.0"

    apply {
        plugin("java")
        plugin("idea")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencyManagement {
        dependencies {
            dependency("org.springframework.boot:spring-boot-autoconfigure:2.3.0.RELEASE")
            dependency("org.springframework.boot:spring-boot-configuration-processor:2.3.0.RELEASE")
            dependency("com.amazonaws:aws-java-sdk-s3:1.11.816")
            dependency("com.amazonaws:aws-java-sdk-core:1.11.816")
            dependency("org.slf4j:slf4j-api:1.6.1")

            dependency("javax.inject:javax.inject:1")
            dependency("org.springframework:spring-context:5.2.6.RELEASE")
            dependency("com.google.api.grpc:proto-google-common-protos:1.16.0")
            dependency("io.grpc:grpc-all:1.29.0")
            dependency("com.google.api.grpc:grpc-google-longrunning-v1:0.1.8")

            dependency("org.junit.jupiter:junit-jupiter-engine:5.3.2")
            dependency("org.junit.jupiter:junit-jupiter-api:5.3.2")

            dependency("org.springframework:spring-test:4.3.0.RELEASE")
            dependency("org.springframework.boot:spring-boot-test:2.3.0.RELEASE")
            dependency("org.springframework.boot:spring-boot-starter-parent:2.1.3.RELEASE")

            dependency("org.mockito:mockito-junit-jupiter:3.3.3")
            dependency("org.junit.jupiter:junit-jupiter:5.5.2")
            dependency("org.mockito:mockito-core:3.1.0")
            dependency("net.sourceforge.jtds:jtds:1.3.1")
            dependency("org.testng:testng:6.9.10")
        }
    }

    java.sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11

    dependencies {

        implementation(group = "org.slf4j", name = "slf4j-api")

        testImplementation("org.springframework.boot", "spring-boot-test")
        testImplementation("org.mockito", "mockito-junit-jupiter")
        testImplementation("org.junit.jupiter", "junit-jupiter")
        testImplementation(group = "org.mockito", name = "mockito-core")

        implementation(kotlin("stdlib-jdk8"))
    }
}
