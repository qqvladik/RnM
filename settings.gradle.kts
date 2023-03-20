pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "RnM"
include(":app")

include(":network")
include(":core")
include(":core-impl")

project(":network").projectDir = File(rootDir, "sources/base/network")
project(":core").projectDir = File(rootDir, "sources/base/core")
project(":core-impl").projectDir = File(rootDir, "sources/base/core_impl")
