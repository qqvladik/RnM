package scripts

import extensions.libs

plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    add("implementation", libs.dagger.core)
    add("ksp", libs.dagger.compiler)
}