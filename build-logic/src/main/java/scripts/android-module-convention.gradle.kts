package scripts

plugins {
    id("com.android.library")
    id("scripts.android-convention")
}

android {
    namespace = "${Configs.applicationPackage.substringBeforeLast('.')}.${
        name.replace(
            "_",
            ""
        )
    }" //namespace must be the same as packageName
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
