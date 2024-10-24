package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class DeleteOwnListUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(listId: String){
        userService.deleteOwnList(listId)
    }
}