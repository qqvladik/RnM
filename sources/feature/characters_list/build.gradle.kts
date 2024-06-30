import extensions.addComposeConfig

plugins {
    scripts.`android-module-convention`
}

android {
    addComposeConfig()
}

dependencies {
    applyDagger()
    applyBaseCompose()

    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Lifecycle.lifecycleRuntime)
    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.converterGson)

    implementation(project(":core"))
    implementation(project(":dependencies"))
    implementation(project(":characters_list_api"))
}