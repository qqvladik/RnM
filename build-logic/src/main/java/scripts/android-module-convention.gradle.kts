package scripts

plugins {
    id("com.android.library")
    id("scripts.android-convention")
}

android {
    namespace = "pl.mankevich.${name.replace("_", "")}" //namespace must be the same as packageName
}
