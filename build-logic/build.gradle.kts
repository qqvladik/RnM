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
}