
description = "Spring test application."

plugins {
    application
    id("java")
    id("idea")
    id("application")
    id("org.springframework.boot") version "2.3.0.RELEASE"
}

application {
  mainClassName = "com.mozafaq.test.springboot.appgateway.Application"
}

dependencies {
    implementation(project(path = ":utils-lib"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.mozafaq.test.springboot.appgateway.Application"
    }

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

fun getRunVmArgs(property: String, defaultValue: String): String {
    return if (System.getProperty(property) != null) System.getProperty(property) else defaultValue;
}

val env = getRunVmArgs("application.environment", "dev")

fun getVmArgs(): List<String> {
    return listOf(
            "-Dvm.args=true",
            "-Dapplication.environment=${env}"
    )
}

task<Exec>("start") {
    dependsOn( "build")
    group = "Execution"
    description = "Start the application post running build and using generated jar file."
    var comdArgs = mutableListOf("java")
    comdArgs.addAll(getVmArgs())
    comdArgs.add("-jar")
    comdArgs.add(jar.archiveFile.get().toString())
    commandLine(comdArgs)
}

task<Exec>("startFast") {
    dependsOn(jar)
    group = "Execution"
    description = "Start the application using last generated jar file."
    var comdArgs = mutableListOf("java")
    comdArgs.addAll(getVmArgs())
    comdArgs.add("-jar")
    comdArgs.add(jar.archiveFile.get().toString())
    commandLine(comdArgs)
}
