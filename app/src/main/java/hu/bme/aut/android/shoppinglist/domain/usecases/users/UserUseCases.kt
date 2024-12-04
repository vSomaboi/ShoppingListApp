package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class UserUseCases(
    val createUser : CreateUserUseCase,
    val updateUser : UpdateUserUseCase,
    val deleteUser : DeleteUserUseCase,
    val sendRequest : SendRequestUseCase,
    val saveOwnList : SaveOwnListUseCase,
    val updateOwnList : UpdateOwnListUseCase,
    val deleteOwnList : DeleteOwnListUseCase,
    val saveSharedList : SaveSharedListUseCase,
    val updateSharedList : UpdateSharedListUseCase,
    val deleteSharedList : DeleteSharedListUseCase,
    val getOwnListsOfUser : GetOwnListsOfUserUseCase,
    val getSharedListsOfUser : GetSharedListsOfUserUseCase,
    val addContact : AddContactUseCase,
    val removeContact : RemoveContactUseCase,
    val getRequests : GetRequestsOfCurrentUserUseCase,
    val getContacts : GetContactsOfCurrentUserUseCase,
    val declineRequest : DeclineRequestUseCase,
    val shareList: ShareListUseCase
)