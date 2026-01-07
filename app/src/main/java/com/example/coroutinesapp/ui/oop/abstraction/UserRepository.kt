package com.example.coroutinesapp.ui.oop.abstraction

import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.RetrofitClient.apiService
import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.model.User

interface UserRepository {
    suspend fun getUser(id: Int): User
}

class RemoteUserRepository : UserRepository {
    private val api = apiService
    override suspend fun getUser(id: Int): User {
        return api.getUser(id)
    }
}
class UserViewModel(
    private val repository: UserRepository
) {
    suspend fun loadUser(id: Int) {
        repository.getUser(id)
    }
}
