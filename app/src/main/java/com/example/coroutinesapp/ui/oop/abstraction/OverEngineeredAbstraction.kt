package com.example.coroutinesapp.ui.oop.abstraction

import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.JsonPlaceholderApi
import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.model.User

interface UserRepo { suspend fun getUser(id: String): User }
interface UserDataSource { suspend fun getUser(id: String): User }
interface RemoteUserDataSource { suspend fun getUser(id: String): User }

class RemoteUserDataSourceImpl(private val api: JsonPlaceholderApi) : RemoteUserDataSource {
    override suspend fun getUser(id: String): User { return api.getUser(id.toInt()) }
}
class UserRepositoryImpl(private val remote: RemoteUserDataSource) : UserRepo {
    override suspend fun getUser(id: String): User { return remote.getUser(id) }
}
class GetUserUseCase(private val repository: UserRepo) {
    suspend operator fun invoke(id: String): User { return repository.getUser(id) }
}
class UserViewModel(private val getUserUseCase: GetUserUseCase)

