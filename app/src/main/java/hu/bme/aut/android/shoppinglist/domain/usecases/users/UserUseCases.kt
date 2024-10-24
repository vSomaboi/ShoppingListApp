package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class UserUseCases(
    userService: UserService
) {
    val createUser = CreateUserUseCase(userService)
    val updateUser = UpdateUserUseCase(userService)
    val deleteUser = DeleteUserUseCase(userService)
    val saveOwnList = SaveOwnListUseCase(userService)
    val updateOwnList = UpdateOwnListUseCase(userService)
    val deleteOwnList = DeleteOwnListUseCase(userService)
    val saveSharedList = SaveSharedListUseCase(userService)
    val updateSharedList = UpdateSharedListUseCase(userService)
    val deleteSharedList = DeleteSharedListUseCase(userService)
    val getOwnListsOfUser = GetOwnListsOfUserUseCase(userService)
    val getSharedListsOfUser = GetSharedListsOfUserUseCase(userService)
}