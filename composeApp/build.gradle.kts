import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.io.InputStreamReader

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
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

val appleId = System.getenv("PROD_MACOS_NOTARIZATION_APPLE_ID") ?: ""
val appleTeamId = System.getenv("PROD_MACOS_NOTARIZATION_TEAM_ID") ?: ""
val notarizationPassword = System.getenv("PROD_MACOS_NOTARIZATION_PWD") ?: ""

android {
    namespace = "de.thomaskuenneth.cmpunitconverter"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

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

dependencies {
    implementation(project(":shared"))
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
}
