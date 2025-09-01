package com.example.coroutinesapp.handleNetworkApi.data.model

// Post.kt
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

// User.kt  
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)

// ApiResponse.kt (for error handling)
sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String) : ApiResponse<T>()
    class Loading<T> : ApiResponse<T>()
}