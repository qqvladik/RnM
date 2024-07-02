package scripts

import Configs
import com.android.build.gradle.BaseExtension

plugins {
    id("org.jetbrains.kotlin.android")
}

fun android(configuration: BaseExtension.() -> Unit) = configure(configuration)

android {
    defaultConfig {
        compileSdkVersion(Configs.compileSdk)
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk

        testInstrumentationRunner = Configs.androidJunitRunner

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true

        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }
}

kotlin {
    jvmToolchain(17)
}
