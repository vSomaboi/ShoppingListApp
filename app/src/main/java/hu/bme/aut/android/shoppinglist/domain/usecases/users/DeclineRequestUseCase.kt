package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class DeclineRequestUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(senderEmail: String){
        userService.declineRequest(senderEmail)
    }
}