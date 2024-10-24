package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

class SaveSharedListUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(listRef: Map<String, String>){
        userService.saveSharedList(listRef)
    }
}