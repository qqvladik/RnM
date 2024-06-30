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

file("${rootProject.projectDir}/sources").listFiles()?.filter { it.isDirectory }
    ?.forEach { source ->
        source.listFiles()?.filter { it.isModule() }?.forEach { module ->
            include(":${module.name}")
            project(":${module.name}").projectDir = module
        }
    }

fun File.isModule(): Boolean = this.isDirectory && this.listFiles()?.any { file ->
    file.name.contains("build.gradle")
} ?: false
