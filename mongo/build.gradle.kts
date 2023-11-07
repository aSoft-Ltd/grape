plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform service implementation for the flix pattern"

kotlin {
    jvm { library() }

    sourceSets {
        jvmMain.dependencies {
            api(db.mongo)
            api(kotlinx.serialization.toml)
            api(libs.koncurrent.later.coroutines)
        }

        jvmTest.dependencies {
            implementation(libs.kommander.coroutines)
        }
    }
}
