import extensions.addComposeConfig

plugins {
    scripts.`android-module-convention`
}

android{
    addComposeConfig()
}

dependencies {
    applyDagger()

    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Network.retrofit)
    implementation(Libs.Compose.navigation)
    implementation(Libs.Lifecycle.lifecycleViewModelCompose)
}