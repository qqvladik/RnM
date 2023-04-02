package scripts

import Configs
import com.android.build.gradle.BaseExtension

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

fun Project.android(configure: BaseExtension.() -> Unit) {
    extensions.configure("android", configure)
}