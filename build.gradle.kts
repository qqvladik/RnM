tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.atomicfu) apply false
}