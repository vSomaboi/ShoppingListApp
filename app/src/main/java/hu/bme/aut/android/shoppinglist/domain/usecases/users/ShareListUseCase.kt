package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

class ShareListUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(contactEmail: String, list: ShoppingList){
        userService.shareListWithContact(contactEmail, list)
    }
}