package scripts

import Configs
import com.android.build.gradle.BaseExtension
import gradle.kotlin.dsl.accessors._8524fec7ee509201879a0215515451b6.kotlin

plugins {
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        compileSdkVersion(Configs.compileSdk)
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk

        testInstrumentationRunner = Configs.androidJunitRunner

//        proguardFiles(
//            getDefaultProguardFile("proguard-android-optimize.txt"),
//            "proguard-rules.pro"
//        )
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

fun Project.android(configure: BaseExtension.() -> Unit) {
    extensions.configure("android", configure)
}