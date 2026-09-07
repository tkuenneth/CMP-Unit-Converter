import java.io.FileInputStream
import java.io.InputStreamReader
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

group = "de.thomaskuenneth.cmpunitconverter"

val content = with(rootProject.file("shared/src/commonMain/kotlin/de/thomaskuenneth/cmpunitconverter/Version.kt")) {
    if (isFile) {
        InputStreamReader(FileInputStream(this), Charsets.UTF_8).use { reader ->
            reader.readText()
        }
    } else {
        error("$absolutePath not found")
    }
}
val (humanReadableVersionString, buildNumber) = with(content) {
    val regexFirst = """const val VERSION = "([^"]+)"""".toRegex()
    val regexSecond = """const val BUILD_NUMBER = "([^"]+)"""".toRegex()
    Pair(
        regexFirst.find(this)?.groupValues?.get(1),
        regexSecond.find(this)?.groupValues?.get(1)
    )
}

android {
    namespace = "de.thomaskuenneth.cmpunitconverter"
    // API 37 is installed as platforms/android-37.0 (not android-37); select that explicitly.
    compileSdk {
        version = release(libs.versions.android.compileSdk.get().toInt()) {
            minorApiLevel = 0
        }
    }

    defaultConfig {
        applicationId = "de.thomaskuenneth.cmpunitconverter"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.compose.components.resources)
    debugImplementation(libs.compose.ui.tooling)
}
