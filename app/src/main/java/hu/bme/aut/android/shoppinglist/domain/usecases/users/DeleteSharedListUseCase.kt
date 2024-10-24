package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class DeleteSharedListUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(listRef: Map<String, String>){
        userService.deleteSharedList(listRef)
    }
}