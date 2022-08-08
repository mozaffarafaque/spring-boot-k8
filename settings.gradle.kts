rootProject.name = "test-fluent"
include(":app-gateway", ":utils-lib")


project(":app-gateway").projectDir = file("app-gateway")
project(":utils-lib").projectDir = file("utils-lib")
