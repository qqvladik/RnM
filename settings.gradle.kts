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

includeBase("network")
includeBase("core")
includeBase("core_impl")
includeBase("storage")

fun includeBase(name: String) {
    include(":$name")
    project(":$name").projectDir = File(rootDir, "sources/base/$name")
}

fun includeFeature(name: String) {
    include(":$name")
    project(":$name").projectDir = File(rootDir, "sources/feature/$name")
}
