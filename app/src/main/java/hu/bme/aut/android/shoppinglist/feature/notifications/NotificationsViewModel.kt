package hu.bme.aut.android.shoppinglist.feature.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
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
class NotificationsViewModel @Inject constructor(
    private val userOperations: UserUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }
    fun onEvent(event: NotificationEvent){
        when(event){
            is NotificationEvent.AcceptClicked -> {
                val senderEmail = event.notificationSender
                viewModelScope.launch {
                    try{
                        userOperations.addContact.invoke(senderEmail)
                        _state.update {
                            it.copy(
                                notificationList = it.notificationList.minus(senderEmail)
                            )
                        }
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.accept_success_message)))
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is NotificationEvent.DeclineClicked -> {
                val senderEmail = event.notificationSender
                viewModelScope.launch {
                    try{
                        userOperations.declineRequest.invoke(senderEmail)
                        _state.update {
                            it.copy(
                                notificationList = it.notificationList.minus(senderEmail)
                            )
                        }
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.decline_success_message)))
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
            is NotificationEvent.LoadNotificationList -> {
                viewModelScope.launch {
                    try{
                        val notifications = userOperations.getRequests.invoke().getOrThrow()
                        _state.update {
                            it.copy(notificationList = notifications)
                        }
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
        }
    }
}

data class NotificationsState(
    val isLoading: Boolean = true,
    val notificationList: List<String> = emptyList()
)

sealed class NotificationEvent{
    data class AcceptClicked(val notificationSender: String): NotificationEvent()
    data class DeclineClicked(val notificationSender: String): NotificationEvent()
    data object LoadNotificationList: NotificationEvent()
}