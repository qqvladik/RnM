repositories {
    mavenCentral()
    google()
}
plugins {
    `kotlin-dsl`
}
dependencies {
    implementation("com.android.tools.build:gradle:8.3.2")
    implementation(kotlin("gradle-plugin", "1.9.24"))
}