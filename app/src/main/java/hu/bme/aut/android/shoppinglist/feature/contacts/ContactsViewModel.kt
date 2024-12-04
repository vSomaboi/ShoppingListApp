package hu.bme.aut.android.shoppinglist.feature.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
import hu.bme.aut.android.shoppinglist.ui.model.UiText
import hu.bme.aut.android.shoppinglist.ui.util.UiEvent
import hu.bme.aut.android.shoppinglist.util.IAddContactDialogUser
import hu.bme.aut.android.shoppinglist.util.IShareDialogUser
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
) : ViewModel(), IAddContactDialogUser, IShareDialogUser {

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
            is ContactEvent.LoadContacts -> {
                viewModelScope.launch {
                    try{
                        val contacts =  userOperations.getContacts.invoke().getOrThrow()
                        _state.update {
                            it.copy(
                                contactList = contacts
                            )
                        }
                    }catch (e: Exception){
                        _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
                    }
                }
            }
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
            is ContactEvent.ShareDialogOpened -> {
                _state.update {
                    it.copy(
                        selectedContact = event.selectedContact,
                        isShareDialogOpen = true
                    )
                }
            }
            is ContactEvent.ShareDialogDismissed -> {
                _state.update {
                    it.copy(
                        isShareDialogOpen = false
                    )
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

    override fun getShoppingLists(): List<ShoppingList> {
        viewModelScope.launch {
            try{
                _state.update {
                    it.copy(
                        shoppingLists = userOperations.getOwnListsOfUser.invoke().getOrThrow()
                    )
                }
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
            }
        }
        return _state.value.shoppingLists
    }

    override fun listSelected(list: ShoppingList) {
        viewModelScope.launch {
            try{
                userOperations.shareList.invoke(
                    _state.value.selectedContact,
                    list
                )
                _uiEvent.send(UiEvent.Notification(UiText.StringResource(R.string.successful_share_message)))
                _state.update {
                    it.copy(
                        isShareDialogOpen = false
                    )
                }
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Notification(UiText.DynamicString(e.message ?: "Unknown error")))
            }
        }
    }
}

data class ContactsState(
    val isLoading: Boolean = true,
    //ContactDialog Data
    val contactList: List<String> = emptyList(),
    val isContactDialogOpen: Boolean = false,
    val contactEmail: String = "",
    //ShareDialog Data
    val isShareDialogOpen: Boolean = false,
    val shoppingLists: List<ShoppingList> = emptyList(),
    val selectedContact: String = ""
)

sealed class ContactEvent{
    data object LoadContacts: ContactEvent()
    data object AddContactDialogOpened: ContactEvent()
    data object AddContactDialogDismissed: ContactEvent()
    data class ContactRemoved(val contact: String): ContactEvent()
    data class ShareDialogOpened(val selectedContact: String): ContactEvent()
    data object ShareDialogDismissed: ContactEvent()
}