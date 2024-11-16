package hu.bme.aut.android.shoppinglist.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.IAddProductDialogUser
import hu.bme.aut.android.shoppinglist.util.productNameMaxLength
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel(), IAddProductDialogUser {
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
        when(event){
            is MainEvent.ListClicked -> {

            }
            is MainEvent.ModifyOwnClicked -> {

            }
            is MainEvent.ModifySharedClicked -> {

            }
            is MainEvent.DialogOpened -> {
                _state.update {
                    it.copy(isDialogOpen = true)
                }
            }
            is MainEvent.DialogDismissed -> {
                _state.update {
                    it.copy(isDialogOpen = false)
                }
            }
        }
    }

    override fun getProductName(): String {
        return _state.value.dialogName
    }

    override fun updateName(input: String) {
        if(input.length <= productNameMaxLength){
            _state.update {
                it.copy(
                    dialogName = input
                )
            }
        }else{
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.too_long_product_name)))
            }
        }
    }

    override fun getLidlPrice(): Int {
        return _state.value.lidlPrice
    }

    override fun updateLidlPrice(input: Int) {
        if(input > 0){
            _state.update {
                it.copy(
                    lidlPrice = input
                )
            }
        }else{
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.negative_price_error)))
            }
        }

    }

    override fun getTescoPrice(): Int {
        return _state.value.tescoPrice
    }

    override fun updateTescoPrice(input: Int) {
        if(input > 0){
            _state.update {
                it.copy(
                    tescoPrice = input
                )
            }
        }else{
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.negative_price_error)))
            }
        }
    }

    override fun getSparPrice(): Int {
        return _state.value.sparPrice
    }

    override fun updateSparPrice(input: Int) {
        if(input > 0){
            _state.update {
                it.copy(
                    sparPrice = input
                )
            }
        }else{
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.negative_price_error)))
            }
        }
    }

    override fun processDialogResult() {
        TODO("Not yet implemented")
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
    val isDialogOpen: Boolean = false,
    val dialogName: String = "",
    val lidlPrice: Int = 0,
    val tescoPrice: Int = 0,
    val sparPrice: Int = 0,
    val numberOfRequests: Int = 0,
    val error: Throwable? = null
)

sealed class MainEvent{
    data class ListClicked(val list: ShoppingList): MainEvent()
    data class ModifyOwnClicked(val list: ShoppingList): MainEvent()
    data class ModifySharedClicked(val list: ShoppingList): MainEvent()
    data object DialogOpened: MainEvent()
    data object DialogDismissed: MainEvent()
}