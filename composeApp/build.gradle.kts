import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.io.InputStreamReader

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "de.thomaskuenneth.cmpunitconverter"
val file = rootProject.file("composeApp/src/commonMain/kotlin/de/thomaskuenneth/cmpunitconverter/Version.kt")
val version = if (file.isFile) {
    with(InputStreamReader(FileInputStream(file), Charsets.UTF_8).use { reader ->
        reader.readText()
    }) {
        val regex = """const val VERSION = "([^"]+)"""".toRegex()
        regex.find(this)?.groupValues?.get(1)
    }
} else error("${file.absolutePath} not found")

val appleId = System.getenv("PROD_MACOS_NOTARIZATION_APPLE_ID") ?: ""
val appleTeamId = System.getenv("PROD_MACOS_NOTARIZATION_TEAM_ID") ?: ""
val notarizationPassword = System.getenv("PROD_MACOS_NOTARIZATION_PWD") ?: ""

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.adaptive)
            implementation(libs.adaptive.layout)
            implementation(compose.material3AdaptiveNavigationSuite)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

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
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "de.thomaskuenneth.cmpunitconverter.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "CMP Unit Converter"
            packageVersion = version.toString()
            description = "A Compose Multiplatform unit converter"
            copyright = "2025 Thomas Kuenneth. All rights reserved."
            vendor = "Thomas Kuenneth"
            macOS {
                bundleID = "de.thomaskuenneth.cmpunitconverter.CMPUnitConverter"
//                iconFile.set(project.file("artwork/Souffleur.icns"))
                signing {
                    sign.set(true)
                    identity.set("Thomas Kuenneth")
                }
                notarization {
                    appleID.set(appleId)
                    password.set(notarizationPassword)
                    teamID.set(appleTeamId)
                }
            }
            windows {
//                iconFile.set(project.file("artwork/Souffleur.ico"))
                menuGroup = "Thomas Kuenneth"
            }
        }
    }
}
