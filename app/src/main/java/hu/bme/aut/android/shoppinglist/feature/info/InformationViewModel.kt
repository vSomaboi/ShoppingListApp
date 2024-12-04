package hu.bme.aut.android.shoppinglist.feature.info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(InformationState())
    val state: StateFlow<InformationState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun onEvent(event: InformationEvent){
        when(event){
            is InformationEvent.SearchInputChanged -> {
                _state.update {
                    it.copy(
                        searchInput = event.value
                    )
                }
            }
            is InformationEvent.LoadChart -> {

            }
        }
    }
}

data class InformationState(
    val isLoading: Boolean = false,
    val searchInput: String = ""
)

sealed class InformationEvent{
    data class SearchInputChanged(val value: String) : InformationEvent()
    data object LoadChart: InformationEvent()
}