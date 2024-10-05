package hu.bme.aut.android.shoppinglist.feature.authentication.login

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
class LoginViewModel @Inject constructor(

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
            is LoginEvent.PasswordChanged -> {
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
            is LoginEvent.PasswordVisibilityChanged -> {
                _state.update {
                    it.copy(isPasswordVisible = it.isPasswordVisible.not())
                }
            }
            is LoginEvent.LoginClicked -> {

            }
        }
    }
}


data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

sealed class LoginEvent{
    data class EmailChanged(val text: String): LoginEvent()
    data class PasswordChanged(val text: String): LoginEvent()
    data object PasswordVisibilityChanged: LoginEvent()
    data object LoginClicked: LoginEvent()
}