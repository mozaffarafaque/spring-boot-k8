
description = "Spark application."

plugins {
    id("java")
    id("idea")
}

dependencies {
    implementation(project(path = ":utils-lib"))

    implementation("org.apache.spark:spark-core_2.12")
    implementation("org.apache.spark:spark-sql_2.12")
    //implementation("org.apache.hadoop:hadoop-aws")
    //implementation("org.apache.hadoop:hadoop-client")
    //implementation("com.amazonaws:aws-java-sdk")
}

val jar by tasks.getting(Jar::class) {

    isZip64 = true
    
    manifest {
        attributes["Implementation-Title"] = "Spark exce"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "com.mozafaq.test.sparkapp.JobRunner"
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))

    exclude("META-INF/*.RSA")
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
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

