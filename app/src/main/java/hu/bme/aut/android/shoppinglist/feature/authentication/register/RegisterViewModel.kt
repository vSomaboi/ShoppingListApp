package hu.bme.aut.android.shoppinglist.feature.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state : StateFlow<RegisterState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        _state.update {
            it.copy(isLoading = false)
        }
    }

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.EmailChanged -> {
                val newValue = event.text
                if(newValue.length <= 35){
                    _state.update {
                        it.copy(email = newValue)
                    }
                }
                else{
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.too_long_email)))
                    }
                }
            }
            is RegisterEvent.PasswordChanged -> {
                val newValue = event.text
                if(newValue.length <= 25){
                    _state.update {
                        it.copy(password = newValue)
                    }
                }
                else{
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.too_long_password)))
                    }
                }
            }
            is RegisterEvent.ConfirmPasswordChanged -> {
                val newValue = event.text
                if(newValue.length <= 25){
                    _state.update {
                        it.copy(password = newValue)
                    }
                }
                else{
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Failure(UiText.StringResource(R.string.too_long_password)))
                    }
                }
            }
            is RegisterEvent.PasswordVisibilityChanged -> {
                _state.update {
                    it.copy(isPasswordVisible = it.isPasswordVisible.not())
                }
            }
            is RegisterEvent.ConfirmPasswordVisibilityChanged -> {
                _state.update {
                    it.copy(isConfirmPasswordVisible = it.isConfirmPasswordVisible.not())
                }
            }
            is RegisterEvent.RegisterClicked -> {

            }
        }
    }
}

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

sealed class RegisterEvent{
    data class EmailChanged(val text: String): RegisterEvent()
    data class PasswordChanged(val text: String): RegisterEvent()
    data class ConfirmPasswordChanged(val text: String): RegisterEvent()
    data object PasswordVisibilityChanged: RegisterEvent()
    data object ConfirmPasswordVisibilityChanged: RegisterEvent()
    data object RegisterClicked: RegisterEvent()
}