package hu.bme.aut.android.shoppinglist.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.model.PriceAtTimePoint
import hu.bme.aut.android.shoppinglist.domain.model.Product
import hu.bme.aut.android.shoppinglist.domain.model.SharedListReference
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProductUseCases
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
import hu.bme.aut.android.shoppinglist.ui.model.ProductUi
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.model.asProduct
import hu.bme.aut.android.shoppinglist.ui.model.asProductUi
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.IAddProductDialogUser
import hu.bme.aut.android.shoppinglist.util.IModifyListDialogUser
import hu.bme.aut.android.shoppinglist.util.ISelectionDialogUser
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
) : ViewModel(), IAddProductDialogUser, IModifyListDialogUser, ISelectionDialogUser {
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
            is MainEvent.LoadLists -> {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                viewModelScope.launch {
                    try{
                        val ownLists = userOperations.getOwnListsOfUser.invoke().getOrThrow()
                        val sharedLists = userOperations.getSharedListsOfUser.invoke().getOrThrow()
                        _state.update {
                            it.copy(
                                ownLists = ownLists,
                                sharedLists = sharedLists,
                                isLoading = false
                            )
                        }
                    }catch (e: Exception){
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is MainEvent.AddProductDialogOpened -> {
                _state.update {
                    it.copy(isProductDialogOpen = true)
                }
            }
            is MainEvent.AddProductDialogDismissed -> {
                _state.update {
                    it.copy(isProductDialogOpen = false)
                }
            }

            is MainEvent.ModifyDialogDismissed -> {
                _state.update {
                    it.copy(
                        isModifyDialogOpen = false
                    )
                }
            }
            is MainEvent.ModifyDialogOpened -> {
                val initialState = event.initialShoppingList
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            modifiedListId = initialState.firebaseId,
                            modifiedListOwnerId = initialState.ownerFirebaseId,
                            modifiedListName = initialState.name,
                            modifiedListItems = initialState.items,
                            isModifyDialogOpen = true,
                            isUpdatingSharedList = event.isSharedList
                        )
                    }
                }
            }
            is MainEvent.DeleteOwnList -> {
                viewModelScope.launch {
                    try{
                        userOperations.deleteOwnList.invoke(event.list.firebaseId)
                        _state.update {
                            it.copy(
                                ownLists = it.ownLists.minus(event.list)
                            )
                        }
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.delete_success_message)))
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is MainEvent.DeleteSharedList -> {
                val deletedList = event.list
                viewModelScope.launch {
                    try{
                        userOperations.deleteSharedList.invoke(
                            SharedListReference(
                                deletedList.ownerFirebaseId,
                                deletedList.firebaseId
                            )
                        )
                        _state.update {
                            it.copy(
                                sharedLists = it.sharedLists.minus(deletedList)
                            )
                        }
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.delete_success_message)))
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is MainEvent.SelectionDialogDismissed -> {
                _state.update {
                    it.copy(
                        isSelectionDialogOpened = false
                    )
                }
            }
            is MainEvent.SelectionDialogOpened -> {
                _state.update {
                    it.copy(
                        isSelectionDialogOpened = true
                    )
                }
            }
        }
    }

    //AddProductDialog Overrides

    override fun getProductName(): String {
        return _state.value.dialogName
    }

    override fun updateProductName(input: String) {
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

    override fun processProductDialogResult() {
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
                        it.copy(isProductDialogOpen = false)
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

    override fun getListName(): String {
        return _state.value.modifiedListName
    }

    override fun updateListName(value: String) {
        _state.update {
            it.copy(
                modifiedListName = value
            )
        }
    }

    override fun openSelectionDialog() {
        _state.update {
            it.copy(
                isSelectionDialogOpened = true
            )
        }
    }

    override fun getSearchBarInput(): String {
        return _state.value.selectionDialogSearchBarInput
    }

    override fun updateSearchBar(input: String) {
        _state.update {
            it.copy(
                selectionDialogSearchBarInput = input
            )
        }
        if(state.value.selectionDialogSearchBarInput.trim().length >= 3){
            viewModelScope.launch {
                try{
                    _state.update {
                        it.copy(
                            selectionDialogItems = productOperations.getProductsNamedAs
                                .invoke(state.value.selectionDialogSearchBarInput.trim())
                                .getOrThrow().map { product -> product.asProductUi() }
                        )
                    }
                }catch (e: Exception){
                    _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                }
            }
        }
    }

    override fun getModifiedItemList(): List<Product> {
        return _state.value.modifiedListItems
    }

    override fun getItemList(): List<ProductUi> {
        return _state.value.selectionDialogItems
    }

    override fun changeSelectedAmountWholePart(item: ProductUi, diff: Int) {
        if(item.selectedAmountWholePart+diff < 0){
            return
        }
        try{
            val idx = _state.value.selectionDialogItems.indexOf(item)
            val newItem = _state.value.selectionDialogItems[idx].copy(selectedAmountWholePart = item.selectedAmountWholePart+diff)
            _state.update {
                it.copy(
                    selectionDialogItems = it.selectionDialogItems.minus(item).plus(newItem)
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
            val idx = _state.value.selectionDialogItems.indexOf(item)
            val newItem = _state.value.selectionDialogItems[idx].copy(selectedAmountWholePart = newAmount.toInt())
            _state.update {
                it.copy(
                    selectionDialogItems = it.selectionDialogItems.minus(item).plus(newItem)
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
            val idx = _state.value.selectionDialogItems.indexOf(item)
            val newItem = _state.value.selectionDialogItems[idx].copy(selectedAmountFractionPart = item.selectedAmountFractionPart+diff)
            _state.update {
                it.copy(
                    selectionDialogItems = it.selectionDialogItems.minus(item).plus(newItem)
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
            val idx = _state.value.selectionDialogItems.indexOf(item)
            val newItem = _state.value.selectionDialogItems[idx].copy(selectedAmountFractionPart = newAmount.toInt())
            _state.update {
                it.copy(
                    selectionDialogItems = it.selectionDialogItems.minus(item).plus(newItem)
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
                it.copy(
                    modifiedListItems = it.modifiedListItems.plus(selectedItem.asProduct()).toMutableList(),
                    isSelectionDialogOpened = false
                )
            }
        }else{
            viewModelScope.launch {
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.invalid_selected_amount_error)))
            }
        }
    }

    override fun deleteListItem(item: Product) {
        _state.update {
            it.copy(
                modifiedListItems = it.modifiedListItems.minus(item)
            )
        }
    }

    override fun updateList() {
        viewModelScope.launch {
            try{
                if(_state.value.isUpdatingSharedList){
                    userOperations.updateSharedList.invoke(
                        ShoppingList(
                            firebaseId = _state.value.modifiedListId,
                            ownerFirebaseId = _state.value.modifiedListOwnerId,
                            name = _state.value.modifiedListName,
                            items = _state.value.modifiedListItems
                        )
                    )
                    _state.update {
                        it.copy(
                            isUpdatingSharedList = false
                        )
                    }
                }else{
                    userOperations.updateOwnList.invoke(
                        ShoppingList(
                            firebaseId = _state.value.modifiedListId,
                            ownerFirebaseId = _state.value.modifiedListOwnerId,
                            name = _state.value.modifiedListName,
                            items = _state.value.modifiedListItems
                        )
                    )
                }
                _state.update {
                    it.copy(
                        isModifyDialogOpen = false
                    )
                }
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.successful_update_message)))
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
            }
        }
    }
}

data class MainState(
    val isLoading: Boolean = true,
    val ownLists: List<ShoppingList> = emptyList(),
    val sharedLists: List<ShoppingList> = emptyList(),
    //AddProductDialog Data
    val isProductDialogOpen: Boolean = false,
    val dialogName: String = "",
    val lidlPrice: Int = 0,
    val tescoPrice: Int = 0,
    val sparPrice: Int = 0,
    //ModifyDialog Data
    val isModifyDialogOpen: Boolean = false,
    val modifiedListId: String = "",
    val modifiedListOwnerId: String = "",
    val modifiedListName: String = "",
    val modifiedListItems: List<Product> = emptyList(),
    //SelectionDialog Data
    var isSelectionDialogOpened: Boolean = false,
    val selectionDialogSearchBarInput: String = "",
    var selectionDialogItems: List<ProductUi> = emptyList(),

    val isUpdatingSharedList: Boolean = false,
    val error: Throwable? = null
)

sealed class MainEvent{
    data object LoadLists: MainEvent()
    data object AddProductDialogOpened: MainEvent()
    data object AddProductDialogDismissed: MainEvent()
    data class ModifyDialogOpened(val initialShoppingList: ShoppingList, val isSharedList: Boolean): MainEvent()
    data class DeleteOwnList(val list: ShoppingList): MainEvent()
    data class DeleteSharedList(val list: ShoppingList): MainEvent()
    data object ModifyDialogDismissed: MainEvent()
    data object SelectionDialogOpened: MainEvent()
    data object SelectionDialogDismissed: MainEvent()
}