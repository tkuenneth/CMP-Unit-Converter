package de.thomaskuenneth.cmpunitconverter

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.KeyboardShortcutInfo
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.thomaskuenneth.cmpunitconverter.AppDestinations
import de.thomaskuenneth.cmpunitconverter.app.App
import de.thomaskuenneth.cmpunitconverter.app.AppViewModel
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.Res
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.distance
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.navigation
import de.thomaskuenneth.cmpunitconverter.shared.generated.resources.temperature
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App { viewModel = it }
        }
    }

    override fun onProvideKeyboardShortcuts(data: MutableList<KeyboardShortcutGroup>, menu: Menu?, deviceId: Int) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId)
        runBlocking {
            with(mutableListOf<KeyboardShortcutInfo>()) {
                add(
                    KeyboardShortcutInfo(
                        getString(Res.string.temperature),
                        KeyEvent.KEYCODE_1,
                        KeyEvent.META_ALT_ON
                    )
                )
                add(
                    KeyboardShortcutInfo(
                        getString(Res.string.distance),
                        KeyEvent.KEYCODE_2,
                        KeyEvent.META_ALT_ON
                    )
                )
                data.add(KeyboardShortcutGroup(getString(Res.string.navigation), this))
            }
        }
    }

    override fun onKeyShortcut(keyCode: Int, event: KeyEvent): Boolean {
        if (::viewModel.isInitialized && event.isAltPressed) {
            when (keyCode) {
                KeyEvent.KEYCODE_1 -> {
                    viewModel.setCurrentDestination(AppDestinations.Temperature)
                    return true
                }

                KeyEvent.KEYCODE_2 -> {
                    viewModel.setCurrentDestination(AppDestinations.Distance)
                    return true
                }
            }
        }
        return super.onKeyShortcut(keyCode, event)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
