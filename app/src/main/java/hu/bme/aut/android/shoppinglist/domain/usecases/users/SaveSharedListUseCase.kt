package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.SharedListReference

class SaveSharedListUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(listRef: SharedListReference){
        userService.saveSharedList(listRef)
    }
}