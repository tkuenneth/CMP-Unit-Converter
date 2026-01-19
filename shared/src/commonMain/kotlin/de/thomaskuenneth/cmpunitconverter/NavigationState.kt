package de.thomaskuenneth.cmpunitconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavigationState {
    var canNavigateBack by mutableStateOf(false)
        private set

    var navigateBack: () -> Unit by mutableStateOf({})
        private set

    fun update(canNavigateBack: Boolean, navigateBack: () -> Unit) {
        this.canNavigateBack = canNavigateBack
        this.navigateBack = navigateBack
    }

    fun clear() {
        this.canNavigateBack = false
        this.navigateBack = {}
    }
}
