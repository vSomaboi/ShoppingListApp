package hu.bme.aut.android.shoppinglist.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.IAddContactDialogUser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val userOperations: UserUseCases
) : ViewModel(), IAddContactDialogUser {

    private val _state = MutableStateFlow(ContactsState())
    val state: StateFlow<ContactsState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
    }

    fun onEvent(event: ContactEvent){
        when(event){
            is ContactEvent.AddContactDialogOpened -> {
                _state.update {
                    it.copy(isContactDialogOpen = true)
                }
            }
            is ContactEvent.AddContactDialogDismissed -> {
                _state.update {
                    it.copy(isContactDialogOpen = false)
                }
            }
            is ContactEvent.ContactRemoved -> {
                viewModelScope.launch {
                    try{
                        val contactToRemove = event.contact
                        userOperations.removeContact.invoke(contactToRemove)
                        _state.update {
                            it.copy(
                                contactList = it.contactList.minus(contactToRemove)
                            )
                        }
                        _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.contact_remove_success)))
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
        }
    }
    override fun getContactEmail(): String {
        return _state.value.contactEmail
    }

    override fun updateContactEmail(input: String) {
        _state.update {
            it.copy(contactEmail = input)
        }
    }

    override fun processContactDialogResult() {
        var addSucceeded: Boolean
        viewModelScope.launch {
            try{
                addSucceeded = userOperations.sendRequest.invoke(_state.value.contactEmail).getOrThrow()
                if(addSucceeded){
                    _state.update {
                        it.copy(isContactDialogOpen = false)
                    }
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.send_request_success)))
                }else{
                    _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.target_not_found_failure)))
                }
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
            }
        }
    }
}

data class ContactsState(
    val isLoading: Boolean = true,
    val contactList: List<String> = listOf(
        "contactemail@gmail.com"
    ),
    val isContactDialogOpen: Boolean = false,
    val contactEmail: String = "",
)

sealed class ContactEvent{
    data object AddContactDialogOpened: ContactEvent()
    data object AddContactDialogDismissed: ContactEvent()
    data class ContactRemoved(val contact: String): ContactEvent()
}