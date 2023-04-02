package scripts

plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("scripts.kotlin-convention")
    id("scripts.android-convention")
}

android {
    defaultConfig {
        applicationId = Configs.applicationPackage
        versionCode = Configs.versionCode
        versionName = Configs.versionName
    }
}

dependencies {
    file("${project.rootDir}/sources").listFiles()?.forEach { source ->
        if (source.isDirectory) {
            source.listFiles()?.forEach { baseModule ->
                if (baseModule.isDirectory && baseModule.isContainsBuildFiles()) {
                    implementation(project(":${baseModule.name}"))
                }
            }
        }
    }
}

fun File.isContainsBuildFiles(): Boolean {
    val buildGradleFiles = this.listFiles { _, name ->
        name.contains("build.gradle")
    }
    return buildGradleFiles?.isNotEmpty() ?: false
}