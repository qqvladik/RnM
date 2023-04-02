package scripts

plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("scripts.kotlin-convention")
    id("scripts.android-convention")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}