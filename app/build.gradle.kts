import extensions.addComposeConfig

plugins {
    scripts.`application-convention`
}

android {
    addComposeConfig()
}

dependencies {
    applyBaseCompose()
    applyDagger()

    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Lifecycle.lifecycleRuntime)
    implementation(Libs.Network.retrofit)
}