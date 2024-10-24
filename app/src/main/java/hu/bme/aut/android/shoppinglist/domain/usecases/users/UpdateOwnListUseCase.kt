package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

class UpdateOwnListUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(list: ShoppingList){
        userService.updateOwnList(list)
    }
}