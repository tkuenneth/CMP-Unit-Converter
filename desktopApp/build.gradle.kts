import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.io.InputStreamReader

plugins {
    kotlin("jvm")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

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

dependencies {
    api(project(":shared"))
    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.koin.core)
    implementation(libs.compose.components.resources)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
}

val macExtraPlistKeys: String
    get() = """
        <key>CFBundleVersion</key>
        <string>${buildNumber}</string>
    """.trim()

compose.desktop {
    application {
        mainClass = "de.thomaskuenneth.cmpunitconverter.MainKt"
        nativeDistributions {
            modules("jdk.unsupported")
            modules("jdk.unsupported.desktop")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "CMP Unit Converter"
            packageVersion = humanReadableVersionString.toString()
            description = "A Compose Multiplatform unit converter"
            copyright = "2025 Thomas Kuenneth. All rights reserved."
            vendor = "Thomas Kuenneth"
            macOS {
                bundleID = "de.thomaskuenneth.cmpunitconverter.CMPUnitConverter"
                iconFile.set(rootProject.file("artwork/app_icon_desktop.icns"))
                signing {
                    sign.set(true)
                    identity.set("Thomas Kuenneth")
                }
                notarization {
                    appleID.set(appleId)
                    password.set(notarizationPassword)
                    teamID.set(appleTeamId)
                }
                infoPlist {
                    extraKeysRawXml = macExtraPlistKeys
                }
            }
            windows {
                iconFile.set(rootProject.file("artwork/app_icon.ico"))
                menuGroup = "Thomas Kuenneth"
            }
        }
        buildTypes.release.proguard {
            configurationFiles.from("rules.pro")
        }
    }
}
