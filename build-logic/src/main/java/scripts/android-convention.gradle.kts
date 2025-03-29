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
}

kotlin {
    jvmToolchain(21)
    compilerOptions.freeCompilerArgs = listOf(
//        "-opt-in=kotlin.Experimental",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.FlowPreview",
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
        "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.paging.ExperimentalPagingApi",
//        "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
//        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
    )
}

dependencies {
    add("implementation", libs.androidx.core.ktx)
}
