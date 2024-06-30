plugins {
    scripts.`android-module-convention`
}

dependencies {
    applyDagger()
    applyChucker()

    implementation(Libs.Kotlin.coreKtx)

    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.converterGson)
    implementation(Libs.Network.converterScalars)
    implementation(Libs.Network.loggingInterceptor)

    api(project(":network_api"))
    implementation(project(":core"))
}