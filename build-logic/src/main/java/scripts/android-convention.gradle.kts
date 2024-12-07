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
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    add("implementation", libs.androidx.core.ktx)
}
