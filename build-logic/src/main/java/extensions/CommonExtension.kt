package extensions

import com.android.build.api.dsl.CommonExtension

fun CommonExtension<*, *, *, *, *, *>.addComposeConfig() {
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }
    }
}
