package scripts

plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("scripts.android-convention")
}

android {
    namespace = Configs.applicationPackage
    defaultConfig {
        applicationId = Configs.applicationPackage
        versionCode = Configs.versionCode
        versionName = Configs.versionName
    }
}

dependencies {
    file("${rootProject.projectDir}/sources").listFiles()?.filter { it.isDirectory }
        ?.forEach { source ->
            source.listFiles()?.filter { it.isModule() }?.forEach { module ->
                implementation(project(":${module.name}"))
            }
        }
}

fun File.isModule(): Boolean = this.isDirectory && this.listFiles()?.any { file ->
    file.name.contains("build.gradle")
} ?: false
