package extensions

import Versions
import com.android.build.api.dsl.CommonExtension

fun CommonExtension<*, *, *, *>.addComposeConfig() {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeKotlinCompilerExt
    }

    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}