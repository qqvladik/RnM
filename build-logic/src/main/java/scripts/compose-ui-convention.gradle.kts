package scripts

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import extensions.addComposeConfig
import extensions.android
import extensions.libs

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    val extension = try {
        the<LibraryExtension>()
    } catch (e: UnknownDomainObjectException) {
        the<ApplicationExtension>()
    }
    extension.addComposeConfig()
}

dependencies {
    add("implementation", platform(libs.androidx.compose.bom))
    add("implementation", libs.androidx.compose.material3)
    add("implementation", libs.androidx.compose.runtime)
    add("implementation", libs.androidx.compose.ui)
    add("debugImplementation", libs.androidx.compose.ui.tooling)
    add("implementation", libs.androidx.compose.ui.tooling.preview)
    add("implementation", libs.androidx.activity.compose)
    add("implementation", libs.androidx.navigation.compose)
}