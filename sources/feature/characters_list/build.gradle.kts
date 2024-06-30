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

    api(project(":characters_list_api"))
    implementation(project(":core"))
    implementation(project(":dependencies"))
}