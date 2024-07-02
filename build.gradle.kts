tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.secrets) apply false
}