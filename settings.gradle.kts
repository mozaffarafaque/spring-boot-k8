rootProject.name = "spring-boot-testapp"
include(":app-gateway",
        ":utils-lib")

project(":app-gateway").projectDir = file("app-gateway")
project(":utils-lib").projectDir = file("utils-lib")