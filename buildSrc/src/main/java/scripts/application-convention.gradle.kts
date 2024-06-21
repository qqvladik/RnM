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
    file("${project.rootDir}/sources").listFiles()?.forEach { source -> //TODO make app module NOT implement features/
        if (source.isDirectory) {
            source.listFiles()?.forEach {
                if (it.isDirectory && it.isModule()) {
                    implementation(project(":${it.name}"))
                }
            }
        }
    }
}

fun File.isModule(): Boolean {
    val buildGradleFiles = this.listFiles { _, name ->
        name.contains("build.gradle")
    }
    return buildGradleFiles?.isNotEmpty() ?: false
}