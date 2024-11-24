package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class AddContactUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(contactEmail: String){
        userService.addContact(contactEmail)
    }
}