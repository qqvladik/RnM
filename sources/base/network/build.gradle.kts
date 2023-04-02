plugins {
    scripts.`android-module-convention`
}

dependencies {
    applyChucker()
    applyDagger()

    implementation(Libs.Kotlin.coreKtx)

    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.converterGson)
    implementation(Libs.Network.converterScalars)
    implementation(Libs.Network.loggingInterceptor)

    implementation(project(":core"))
}