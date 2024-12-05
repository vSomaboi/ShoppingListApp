package hu.bme.aut.android.shoppinglist.feature.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProductUseCases
import hu.bme.aut.android.shoppinglist.domain.usecases.shops.ShopUseCases
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.IPriceInfoChartUser
import hu.bme.aut.android.shoppinglist.util.IProvidePriceDialogUser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val productOperations: ProductUseCases,
    private val shopOperations: ShopUseCases
) : ViewModel(), IPriceInfoChartUser, IProvidePriceDialogUser {
    private val _state = MutableStateFlow(InformationState())
    val state: StateFlow<InformationState> = _state

    private val _chartState = MutableStateFlow(InfoChartState())
    override val chartState: StateFlow<InfoChartState> = _chartState

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
                viewModelScope.launch {
                    try{
                        val productData = productOperations.getProductWithExactName.invoke(_state.value.searchInput).getOrThrow()
                        val lidlPriceList = mutableListOf<Int>()
                        val tescoPriceList = mutableListOf<Int>()
                        val sparPriceList = mutableListOf<Int>()
                        productData.lidlPrices.takeLast(30).forEach{ lidlPriceList.add(it.price) }
                        productData.tescoPrices.takeLast(30).forEach { tescoPriceList.add(it.price) }
                        productData.sparPrices.takeLast(30).forEach { sparPriceList.add(it.price) }
                        _chartState.update {
                            it.copy(
                                chartModel = CartesianChartModel(
                                    LineCartesianLayerModel.build { series(y  = lidlPriceList) },
                                    LineCartesianLayerModel.build { series(y = tescoPriceList) },
                                    LineCartesianLayerModel.build { series(y = sparPriceList) }
                                )
                            )
                        }
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is InformationEvent.ProvideInfoDialogOpened -> {
                _state.update {
                    it.copy(
                        isInfoDialogOpen = true
                    )
                }
            }
            is InformationEvent.ProvideInfoDialogDismissed -> {
                _state.update {
                    it.copy(
                        isInfoDialogOpen = false
                    )
                }
            }
            is InformationEvent.WaitInfoButtonClicked -> {
                val shopName = event.shopName
                _state.update {
                    it.copy(
                        selectedShop = shopName
                    )
                }
                viewModelScope.launch {
                    try{
                        val shopInfo = shopOperations.getShopData.invoke(shopName).getOrThrow()
                        _state.update {
                            it.copy(
                                estimatedWaitTime = shopInfo.estimatedWaitingTime
                            )
                        }
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is InformationEvent.ContributeButtonPressed -> {
                viewModelScope.launch {
                    try{
                        shopOperations.updateWaitingTime.invoke(
                            _state.value.selectedShop,
                            _state.value.contributionInput.trim().toInt()
                        )
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.contribution_appreciation)))
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is InformationEvent.ContributionInputChanged -> {
                _state.update {
                    it.copy(
                        contributionInput = event.input
                    )
                }
            }
        }
    }

    override fun getSearchbarInput(): String {
        return _state.value.dialogSearchbarInput
    }
    override fun updateSearchbarInput(value: String) {
        _state.update {
            it.copy(
                dialogSearchbarInput = value
            )
        }
    }
    override fun getLidlPrice(): String {
        return _state.value.lidlPrice
    }

    override fun updateLidlPrice(value: String) {
        _state.update {
            it.copy(
                lidlPrice = value
            )
        }
    }
    override fun getTescoPrice(): String {
        return _state.value.tescoPrice
    }

    override fun updateTescoPrice(value: String) {
        _state.update {
            it.copy(
                tescoPrice = value
            )
        }
    }

    override fun getSparPrice(): String {
        return _state.value.sparPrice
    }

    override fun updateSparPrice(value: String) {
        _state.update {
            it.copy(
                sparPrice = value
            )
        }
    }

    override fun processInfoDialogResult() {
        viewModelScope.launch {
            try{
                var anySuccess = false
                if(_state.value.lidlPrice.isNotBlank()){
                    val isSuccess = productOperations.providePriceInfo.invoke(
                        _state.value.dialogSearchbarInput,
                        Pair("lidl", _state.value.lidlPrice.trim().toInt())
                    ).getOrThrow()
                    if(isSuccess) anySuccess = true
                }
                if(_state.value.tescoPrice.isNotBlank()){
                    val isSuccess = productOperations.providePriceInfo.invoke(
                        _state.value.dialogSearchbarInput,
                        Pair("tesco", _state.value.tescoPrice.trim().toInt())
                    ).getOrThrow()
                    if(isSuccess) anySuccess = true
                }
                if(_state.value.sparPrice.isNotBlank()){
                    val isSuccess = productOperations.providePriceInfo.invoke(
                        _state.value.dialogSearchbarInput,
                        Pair("spar", _state.value.sparPrice.trim().toInt())
                    ).getOrThrow()
                    if(isSuccess) anySuccess = true
                }
                if(anySuccess){
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.info_providing_success_message)))
                }
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
            }
        }
        _state.update {
            it.copy(
                isInfoDialogOpen = false
            )
        }
    }
}

data class InformationState(
    val isLoading: Boolean = false,
    val searchInput: String = "",
    val selectedShop: String = "",
    val estimatedWaitTime: Int = 0,
    val contributionInput: String = "",
    //InfoDialog Data
    val dialogSearchbarInput: String = "",
    val isInfoDialogOpen: Boolean = false,
    val lidlPrice: String = "",
    val tescoPrice: String = "",
    val sparPrice: String = ""
)

data class InfoChartState(
    val chartModel: CartesianChartModel = CartesianChartModel(LineCartesianLayerModel.build { series(0,1,2) })
)

sealed class InformationEvent{
    data class SearchInputChanged(val value: String) : InformationEvent()
    data object LoadChart: InformationEvent()
    data object ProvideInfoDialogOpened: InformationEvent()
    data object ProvideInfoDialogDismissed: InformationEvent()
    data class WaitInfoButtonClicked(val shopName: String): InformationEvent()
    data object ContributeButtonPressed: InformationEvent()
    data class ContributionInputChanged(val input: String): InformationEvent()
}