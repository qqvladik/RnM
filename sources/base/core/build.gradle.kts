plugins {
    scripts.`android-module-convention`
}

dependencies {
//    applyDagger()
    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Dagger.dagger)//only for @ApplicationContext, so TODO remove
    implementation(Libs.Room.ktx)
    implementation(Libs.Network.retrofit)
}