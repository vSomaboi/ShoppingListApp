package hu.bme.aut.android.shoppinglist.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.model.PriceAtTimePoint
import hu.bme.aut.android.shoppinglist.domain.model.Product
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProductUseCases
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
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
import java.lang.NumberFormatException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productOperations: ProductUseCases,
    private val userOperations: UserUseCases
) : ViewModel(), IAddProductDialogUser {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        viewModelScope.launch {
            try{
                userOperations.createUser.invoke()
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message.toString())))
            }
        }
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
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.too_long_product_name)))
            }
        }
    }

    override fun getLidlPrice(): String {
        return if(_state.value.lidlPrice == 0){
            ""
        }else{
            _state.value.lidlPrice.toString()
        }
    }

    override fun updateLidlPrice(input: String) {
        try{
            if(input.toInt() > 0){
                _state.update {
                    it.copy(
                        lidlPrice = input.toInt()
                    )
                }
            }else{
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.negative_price_error)))
                }
            }
        }catch (e: NumberFormatException){
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.invalid_number_format_error)))
            }
        }
    }

    override fun getTescoPrice(): String {
        return if(_state.value.tescoPrice == 0){
            ""
        }else{
            _state.value.tescoPrice.toString()
        }
    }

    override fun updateTescoPrice(input: String) {
        try{
            if(input.toInt() > 0){
                _state.update {
                    it.copy(
                        tescoPrice = input.toInt()
                    )
                }
            }else{
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.negative_price_error)))
                }
            }
        }catch (e: NumberFormatException){
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.invalid_number_format_error)))
            }
        }

    }

    override fun getSparPrice(): String {
        return if(_state.value.sparPrice == 0){
            ""
        }else{
            _state.value.sparPrice.toString()
        }
    }

    override fun updateSparPrice(input: String) {
        try{
            if(input.toInt() > 0){
                _state.update {
                    it.copy(
                        sparPrice = input.toInt()
                    )
                }
            }else{
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.negative_price_error)))
                }
            }
        }catch (e: NumberFormatException){
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.invalid_number_format_error)))
            }
        }

    }

    override fun processDialogResult() {
        var addSucceeded: Boolean
        viewModelScope.launch {
            try {
                val currentTime = Timestamp(Date())
                addSucceeded = productOperations.saveProduct.invoke(
                    Product(
                        name = _state.value.dialogName,
                        lidlPrices = if (_state.value.lidlPrice > 0) {
                            listOf(
                                PriceAtTimePoint(_state.value.lidlPrice, currentTime)
                            )
                        } else {
                            emptyList()
                        },
                        tescoPrices = if (_state.value.tescoPrice > 0) {
                            listOf(
                                PriceAtTimePoint(_state.value.tescoPrice, currentTime)
                            )
                        } else {
                            emptyList()
                        },
                        sparPrices = if (_state.value.sparPrice > 0) {
                            listOf(
                                PriceAtTimePoint(_state.value.sparPrice, currentTime)
                            )
                        } else {
                            emptyList()
                        }
                    )
                ).getOrThrow()
                if (addSucceeded) {
                    _state.update {
                        it.copy(isDialogOpen = false)
                    }
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.add_dialog_success)))
                } else {
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.product_already_exists_failure)))
                }
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
            }
        }
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