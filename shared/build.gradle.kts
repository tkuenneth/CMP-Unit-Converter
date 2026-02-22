import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidLibrary {
        namespace = "de.thomaskuenneth.cmpunitconverter.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        androidResources { enable = true }
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    val isMacOS = System.getProperty("os.name").lowercase().contains("mac os x")
    if (isMacOS) listOf(
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
        all {
            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.material3.adaptive.navigation.suite)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.ui.tooling.preview)

            implementation(libs.navigation.compose)
            implementation(libs.adaptive)
            implementation(libs.adaptive.layout)
            implementation(libs.adaptive.navigation)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.datastore.preferences)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewModel)

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.kotlinx.datetime)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.resources {
    packageOfResClass = "de.thomaskuenneth.cmpunitconverter.shared.generated.resources"
    publicResClass = true
}

dependencies {
    val isMacOS = System.getProperty("os.name").lowercase().contains("mac os x")
    mutableListOf(
        "kspAndroid",
        "kspDesktop"
    ).apply {
        if (isMacOS)
            addAll(
                listOf(
                    "kspIosArm64",
                    "kspIosX64",
                    "kspIosSimulatorArm64"
                )
            )
    }.forEach {
        add(it, libs.androidx.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}
