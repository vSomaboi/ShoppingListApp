package hu.bme.aut.android.shoppinglist.ui.util

import hu.bme.aut.android.shoppinglist.ui.model.UiText

sealed class UiEvent {
    data object Success: UiEvent()
    data class Notification(val message: UiText): UiEvent()
}