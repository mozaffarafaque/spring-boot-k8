rootProject.name = "spring-boot-testapp"
include(":app-gateway",
        ":utils-lib",
        ":app-worker-compute")

project(":app-gateway").projectDir = file("app-gateway")
project(":utils-lib").projectDir = file("utils-lib")
project(":app-worker-compute").projectDir = file("app-worker-compute")