package scripts

import extensions.android
import extensions.libs

plugins {
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        compileSdkVersion(35)
        minSdk = 21
        targetSdk = 35

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lintOptions {
        disable("EmptyNavDeepLink")
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    add("implementation", libs.androidx.core.ktx)
}
