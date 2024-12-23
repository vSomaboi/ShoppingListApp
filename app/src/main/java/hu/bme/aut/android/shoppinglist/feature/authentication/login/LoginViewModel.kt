package hu.bme.aut.android.shoppinglist.feature.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.usecases.auth.AuthUseCases
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.emailMaxLength
import hu.bme.aut.android.shoppinglist.util.passwordMaxLength
import hu.bme.aut.android.shoppinglist.util.passwordMinLength
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authOperations: AuthUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state : StateFlow<LoginState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        _state.update {
            it.copy(isLoading = false)
        }
    }

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EmailChanged -> {
                val newValue = event.input
                if(newValue.length <= emailMaxLength){
                    _state.update {
                        it.copy(email = newValue)
                    }
                }
                else{
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.too_long_email)))
                    }
                }
            }
            is LoginEvent.PasswordChanged -> {
                val newValue = event.input
                if(newValue.length <= passwordMaxLength){
                    _state.update {
                        it.copy(password = newValue)
                    }
                }
                else if(newValue.trim().length < passwordMinLength){
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.too_short_password)))
                    }
                }
                else{
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.too_long_password)))
                    }
                }
            }
            is LoginEvent.PasswordVisibilityChanged -> {
                _state.update {
                    it.copy(isPasswordVisible = it.isPasswordVisible.not())
                }
            }
            is LoginEvent.LoginClicked -> {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                viewModelScope.launch {
                    try{
                        if(state.value.email.isBlank()){
                            _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.empty_email_field)))
                        }
                        else if(state.value.password.isBlank()){
                            _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.empty_password_field)))
                        }
                        else{
                            authOperations.authenticateUser(
                                state.value.email,
                                state.value.password
                            )
                            _uiEvent.send(UiEvent.Success)
                        }
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}


data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)

sealed class LoginEvent{
    data class EmailChanged(val input: String): LoginEvent()
    data class PasswordChanged(val input: String): LoginEvent()
    data object PasswordVisibilityChanged: LoginEvent()
    data object LoginClicked: LoginEvent()
}