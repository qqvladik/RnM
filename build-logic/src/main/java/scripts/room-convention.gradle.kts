package scripts

import androidx.room.gradle.RoomExtension
import extensions.android
import extensions.libs

plugins {
    id("androidx.room")
    id("com.google.devtools.ksp")
}

android {
    ksp {
        arg("room.generateKotlin", "true")
    }
    configure<RoomExtension> {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    add("implementation", libs.androidx.room.ktx)
    add("implementation", libs.androidx.room.runtime)
    add("ksp", libs.androidx.room.compiler)
}