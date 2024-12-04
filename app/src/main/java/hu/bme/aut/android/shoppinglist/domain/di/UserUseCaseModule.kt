package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.usecases.users.AddContactUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.CreateUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.DeclineRequestUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.DeleteOwnListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.DeleteSharedListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.DeleteUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.GetContactsOfCurrentUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.GetOwnListsOfUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.GetRequestsOfCurrentUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.GetSharedListsOfUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.RemoveContactUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.SaveOwnListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.SaveSharedListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.SendRequestUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.ShareListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UpdateOwnListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UpdateSharedListUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UpdateUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    @Singleton
    fun provideAddContactUseCase(
        userService: UserService
    ) : AddContactUseCase = AddContactUseCase(userService)
    @Provides
    @Singleton
    fun provideCreateUserUseCase(
        userService: UserService
    ) : CreateUserUseCase = CreateUserUseCase(userService)
    @Provides
    @Singleton
    fun provideDeclineRequestUseCase(
        userService: UserService
    ) : DeclineRequestUseCase = DeclineRequestUseCase(userService)
    @Provides
    @Singleton
    fun provideDeleteOwnListUseCase(
        userService: UserService
    ) : DeleteOwnListUseCase = DeleteOwnListUseCase(userService)
    @Provides
    @Singleton
    fun provideDeleteSharedListUseCase(
        userService: UserService
    ) : DeleteSharedListUseCase = DeleteSharedListUseCase(userService)
    @Provides
    @Singleton
    fun provideDeleteUserUseCase(
        userService: UserService
    ) : DeleteUserUseCase = DeleteUserUseCase(userService)
    @Provides
    @Singleton
    fun provideGetContactsOfCurrentUserUseCase(
        userService: UserService
    ) : GetContactsOfCurrentUserUseCase = GetContactsOfCurrentUserUseCase(userService)
    @Provides
    @Singleton
    fun provideGetOwnListsOfUserUseCase(
        userService: UserService
    ) : GetOwnListsOfUserUseCase = GetOwnListsOfUserUseCase(userService)
    @Provides
    @Singleton
    fun provideGetRequestsOfCurrentUserUseCase(
        userService: UserService
    ) : GetRequestsOfCurrentUserUseCase = GetRequestsOfCurrentUserUseCase(userService)
    @Provides
    @Singleton
    fun provideGetSharedListsOfUserUseCase(
        userService: UserService
    ) : GetSharedListsOfUserUseCase = GetSharedListsOfUserUseCase(userService)
    @Provides
    @Singleton
    fun provideRemoveContactUseCase(
        userService: UserService
    ) : RemoveContactUseCase = RemoveContactUseCase(userService)
    @Provides
    @Singleton
    fun provideSaveOwnListUseCase(
        userService: UserService
    ) : SaveOwnListUseCase = SaveOwnListUseCase(userService)
    @Provides
    @Singleton
    fun provideSaveSharedListUseCase(
        userService: UserService
    ) : SaveSharedListUseCase = SaveSharedListUseCase(userService)
    @Provides
    @Singleton
    fun provideSendRequestUseCase(
        userService: UserService
    ) : SendRequestUseCase = SendRequestUseCase(userService)
    @Provides
    @Singleton
    fun provideUpdateOwnListUseCase(
        userService: UserService
    ) : UpdateOwnListUseCase = UpdateOwnListUseCase(userService)
    @Provides
    @Singleton
    fun provideUpdateSharedListUseCase(
        userService: UserService
    ) : UpdateSharedListUseCase = UpdateSharedListUseCase(userService)
    @Provides
    @Singleton
    fun provideUpdateUserUseCase(
        userService: UserService
    ) : UpdateUserUseCase = UpdateUserUseCase(userService)
    @Provides
    @Singleton
    fun provideShareListUseCase(
        userService: UserService
    ) : ShareListUseCase = ShareListUseCase(userService)

    @Provides
    @Singleton
    fun provideUserUseCases(
        createUser : CreateUserUseCase,
        updateUser: UpdateUserUseCase,
        deleteUser: DeleteUserUseCase,
        sendRequest: SendRequestUseCase,
        saveOwnList: SaveOwnListUseCase,
        updateOwnList: UpdateOwnListUseCase,
        deleteOwnList: DeleteOwnListUseCase,
        saveSharedList: SaveSharedListUseCase,
        updateSharedList: UpdateSharedListUseCase,
        deleteSharedList: DeleteSharedListUseCase,
        getOwnListsOfUser: GetOwnListsOfUserUseCase,
        getSharedListsOfUser: GetSharedListsOfUserUseCase,
        addContact: AddContactUseCase,
        removeContact: RemoveContactUseCase,
        getRequests: GetRequestsOfCurrentUserUseCase,
        getContacts: GetContactsOfCurrentUserUseCase,
        declineRequest: DeclineRequestUseCase,
        shareList: ShareListUseCase
    ) : UserUseCases = UserUseCases(
        createUser = createUser,
        updateUser = updateUser,
        deleteUser = deleteUser,
        sendRequest = sendRequest,
        saveOwnList = saveOwnList,
        updateOwnList = updateOwnList,
        deleteOwnList = deleteOwnList,
        saveSharedList = saveSharedList,
        updateSharedList = updateSharedList,
        deleteSharedList = deleteSharedList,
        getOwnListsOfUser = getOwnListsOfUser,
        getSharedListsOfUser = getSharedListsOfUser,
        addContact = addContact,
        removeContact = removeContact,
        getRequests = getRequests,
        getContacts = getContacts,
        declineRequest = declineRequest,
        shareList = shareList
    )
}