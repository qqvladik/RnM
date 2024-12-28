package scripts

plugins {
    id("com.android.application")
    id("scripts.android-convention")
}

android {
    val appPackage = "pl.mankevich.rnm"
    namespace = appPackage
    defaultConfig {
        applicationId = appPackage
        versionCode = 1
        versionName = "1.0.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false//TODO true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }
}

dependencies {
    file("${rootProject.projectDir}/sources").listFiles()
        ?.filter { it.isDirectory && !it.name.contains("api") }
        ?.forEach { source ->
            source.listFiles()?.filter { it.isModule() }?.forEach { module ->
                implementation(project(":${module.name}"))
            }
        }
}

fun File.isModule(): Boolean = this.isDirectory && this.listFiles()?.any { file ->
    file.name.contains("build.gradle")
} ?: false
