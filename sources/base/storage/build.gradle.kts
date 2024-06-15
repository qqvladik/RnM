plugins {
    scripts.`android-module-convention`
}

dependencies {
    implementation(Libs.Kotlin.coreKtx)
    applyRoom()
    applyDagger()

    implementation(project(":core"))
}