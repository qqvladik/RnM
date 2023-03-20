import pl.mankevich.rnm.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = Configs.compileSdk

    defaultConfig {
        applicationId = Configs.applicationPackage
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = Configs.androidJunitRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = Configs.freeCompilerArgs
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeKotlinCompilerExt
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Lifecycle.lifecycleRuntime)

    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.material3)
    implementation(Libs.Compose.preview)
    implementation(Libs.Compose.activity)

    implementation(Libs.Dagger.dagger)
    kapt(Libs.Dagger.compiler)

    implementation(Libs.Network.retrofit)

    implementation(project(":core-impl"))
    implementation(project(":core"))
    implementation(project(":network"))
}