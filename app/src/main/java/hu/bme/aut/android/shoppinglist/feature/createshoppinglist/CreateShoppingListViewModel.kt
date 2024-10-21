package hu.bme.aut.android.shoppinglist.feature.createshoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.model.Product
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.listNameMaxLength
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateShoppingListViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(CreateShoppingListState())
    val state : StateFlow<CreateShoppingListState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun onEvent(event: CreateShoppingListEvent){
        when(event){
            is CreateShoppingListEvent.NameChanged -> {
                val newValue = event.input
                if(newValue.length <= listNameMaxLength){
                    _state.update {
                        it.copy(
                            listName = newValue
                        )
                    }
                }
                else{
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.too_long_list_name)))
                    }
                }
            }
            is CreateShoppingListEvent.DeleteListItem -> {

            }
            is CreateShoppingListEvent.AddButtonClicked -> {

            }
        }
    }
}

data class CreateShoppingListState(
    val listName: String = "",
    val items: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

sealed class CreateShoppingListEvent{
    data class NameChanged(val input: String): CreateShoppingListEvent()

    data object AddButtonClicked : CreateShoppingListEvent()

    data class DeleteListItem(val item: Product): CreateShoppingListEvent()
}