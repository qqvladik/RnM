repositories {
    mavenCentral()
    google()
}
plugins {
    `kotlin-dsl`
}
dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.room.gradlePlugin)
    // Accessing the Version Catalog in convention plugins according to
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    // TODO. But this is a hack, so there is need to monitor for any updates.
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}