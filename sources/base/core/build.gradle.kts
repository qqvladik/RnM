plugins {
    scripts.`android-module-convention`
}

dependencies {
    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Dagger.dagger)//only for @ApplicationContext, so TODO remove in future
    implementation(Libs.Room.ktx)
    implementation(Libs.Network.retrofit)
}