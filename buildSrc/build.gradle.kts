repositories {
    mavenCentral()
    google()
}
plugins {
    `kotlin-dsl`
}
dependencies {
    implementation("com.android.tools.build:gradle:7.2.2")
    implementation(kotlin("gradle-plugin", "1.8.0"))
}