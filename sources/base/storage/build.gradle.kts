plugins {
    scripts.`android-module-convention`
}

dependencies {
    applyDagger()
    applyRoom()

    implementation(Libs.Kotlin.coreKtx)

    implementation(project(":core"))
    implementation(project(":storage_api"))
}