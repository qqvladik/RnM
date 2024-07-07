package scripts

import Configs
import extensions.android
import extensions.libs

plugins {
    id("org.jetbrains.kotlin.android")
}

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

dependencies {
    add("implementation", libs.androidx.core.ktx)
}
