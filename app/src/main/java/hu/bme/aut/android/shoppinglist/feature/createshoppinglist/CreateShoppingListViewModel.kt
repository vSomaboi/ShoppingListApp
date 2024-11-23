package hu.bme.aut.android.shoppinglist.feature.createshoppinglist


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.model.Product
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProductUseCases
import hu.bme.aut.android.shoppinglist.ui.model.ProductUi
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.model.asProduct
import hu.bme.aut.android.shoppinglist.ui.model.asProductUi
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.ISelectionDialogUser
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
    //private val productOperations: ProductUseCases
) : ViewModel(), ISelectionDialogUser {
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
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.too_long_list_name)))
                    }
                }
            }
            is CreateShoppingListEvent.DeleteListItem -> {
                val deletedItem = event.item
                _state.update {
                    it.apply {
                        items.remove(deletedItem)
                    }
                }

            }
            is CreateShoppingListEvent.AddButtonClicked -> {
                _state.update {
                    it.copy(
                        isDialogOpened = true
                    )
                }
            }
            is CreateShoppingListEvent.CreateButtonClicked -> {

            }
            is CreateShoppingListEvent.DialogDismissed -> {
                _state.update {
                    it.copy(
                        isDialogOpened = false
                    )
                }
            }
        }
    }

    override fun getItemList(): List<ProductUi> {
        return _state.value.dialogItems
    }

    override fun changeSelectedAmountWholePart(item: ProductUi, diff: Int) {
        if(item.selectedAmountWholePart+diff < 0){
            return
        }
        try{
            val idx = _state.value.dialogItems.indexOf(item)
            val newItem = _state.value.dialogItems[idx].copy(selectedAmountWholePart = item.selectedAmountWholePart+diff)
            _state.update {
                it.copy(
                    dialogItems = it.dialogItems.minus(item).plus(newItem)
                )
            }
        }catch (e: IndexOutOfBoundsException){
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.unexpected_error_message)))
            }
        }
    }

    override fun setSelectedAmountWholePart(item: ProductUi, newAmount: String) {
        try{
            val idx = _state.value.dialogItems.indexOf(item)
            val newItem = _state.value.dialogItems[idx].copy(selectedAmountWholePart = newAmount.toInt())
            _state.update {
                it.copy(
                    dialogItems = it.dialogItems.minus(item).plus(newItem)
                )
            }
        }catch (e: IndexOutOfBoundsException){
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.unexpected_error_message)))
            }
        }
    }

    override fun changeSelectedAmountFractionPart(item: ProductUi, diff: Int) {
        if(item.selectedAmountFractionPart+diff < 0){
            return
        }
        try{
            val idx = _state.value.dialogItems.indexOf(item)
            val newItem = _state.value.dialogItems[idx].copy(selectedAmountFractionPart = item.selectedAmountFractionPart+diff)
            _state.update {
                it.copy(
                    dialogItems = it.dialogItems.minus(item).plus(newItem)
                )
            }
        }catch (e: IndexOutOfBoundsException){
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.unexpected_error_message)))
            }
        }
    }

    override fun setSelectedAmountFractionPart(item: ProductUi, newAmount: String) {
        try{
            val idx = _state.value.dialogItems.indexOf(item)
            val newItem = _state.value.dialogItems[idx].copy(selectedAmountFractionPart = newAmount.toInt())
            _state.update {
                it.copy(
                    dialogItems = it.dialogItems.minus(item).plus(newItem)
                )
            }
        }catch (e: Exception){
            viewModelScope.launch {
                if(e.javaClass == NumberFormatException::class.java){
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.invalid_number_format_error)))
                }else{
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.unexpected_error_message)))
                }
            }
        }
    }

    override fun processSelectionResult(selectedItem: ProductUi) {
        if(selectedItem.selectedAmountWholePart > 0 || selectedItem.selectedAmountFractionPart > 0){
            _state.update {
                it.apply {
                    items.add(selectedItem.asProduct())
                    isDialogOpened = false
                }
            }
        }else{
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.invalid_selected_amount_error)))
            }
        }

    }

    override fun getSearchBarInput(): String {
        return _state.value.dialogSearchBarInput
    }

    override fun updateSearchBar(input: String) {
        _state.update {
            it.copy(
                dialogSearchBarInput = input
            )
        }
        /*if(state.value.dialogSearchBarInput.trim().length >= 3){
            viewModelScope.launch {
                try{
                    _state.update {
                        it.copy(
                            dialogItems = productOperations.getProductsNamedAsUseCase
                                .invoke(state.value.dialogSearchBarInput.trim())
                                .getOrThrow().map { product ->  product.asProductUi() }.toMutableList()
                        )
                    }

                }catch (e: Exception){
                    _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                }
            }
        }*/
    }
}

data class CreateShoppingListState(
    val listName: String = "",
    val items: MutableList<Product> = mutableListOf(),
    var isDialogOpened: Boolean = false,
    val dialogSearchBarInput: String = "",
    var dialogItems: List<ProductUi> =listOf(
        ProductUi(
            id = "1",
            name = "teszt",
            selectedAmountWholePart = 0,
            selectedAmountFractionPart = 0
        )
    ),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

sealed class CreateShoppingListEvent{
    data class NameChanged(val input: String): CreateShoppingListEvent()
    data object AddButtonClicked : CreateShoppingListEvent()
    data class DeleteListItem(val item: Product): CreateShoppingListEvent()
    data object CreateButtonClicked: CreateShoppingListEvent()
    data object DialogDismissed: CreateShoppingListEvent()
}