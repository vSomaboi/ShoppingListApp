package hu.bme.aut.android.shoppinglist.feature.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun onEvent(event: MainEvent){

    }
}

data class MainState(
    val isLoading: Boolean = true,
    val ownLists: List<ShoppingList> = listOf(
        ShoppingList(
            firebaseId = "fireId",
            ownerFirebaseId = "ownerId",
            name = "test"
        )
    ),
    val sharedLists: List<ShoppingList> = listOf(
        ShoppingList(
            firebaseId = "fireId2",
            ownerFirebaseId = "ownerId",
            name = "test2"
        )
    ),
    val numberOfRequests: Int = 0,
    val error: Throwable? = null
)

sealed class MainEvent{
    data class ListClicked(val list: ShoppingList): MainEvent()
    data class ModifyOwnClicked(val list: ShoppingList): MainEvent()
    data class ModifySharedClicked(val list: ShoppingList): MainEvent()
    data object AddButtonClicked: MainEvent()
    data object NotificationsButtonClicked: MainEvent()
    data object NewContactButtonClicked: MainEvent()
}