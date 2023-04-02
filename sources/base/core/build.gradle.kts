plugins {
    scripts.`android-module-convention`
}

dependencies {
    applyDagger()
    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Network.retrofit)
}