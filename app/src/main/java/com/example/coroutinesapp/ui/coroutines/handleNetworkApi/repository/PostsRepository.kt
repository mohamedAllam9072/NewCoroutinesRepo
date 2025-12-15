package com.example.coroutinesapp.ui.coroutines.handleNetworkApi.repository

import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.RetrofitClient.apiService
import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.model.ApiResponse
import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostsRepository {
    private val api = apiService
    
    // Get all posts with error handling
    suspend fun getAllPosts(): ApiResponse<List<Post>> {
        return try {
            val posts = api.getPosts()
            ApiResponse.Success(posts)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "Unknown error occurred")
        }
    }
    
    // Get post by ID
    suspend fun getPostById(id: Int): ApiResponse<Post> {
        return try {
            val post = api.getPost(id)
            ApiResponse.Success(post)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "Failed to fetch post")
        }
    }
    
    // Get posts by user
    suspend fun getPostsByUser(userId: Int): ApiResponse<List<Post>> {
        return try {
            val posts = api.getPostsByUser(userId)
            ApiResponse.Success(posts)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "Failed to fetch user posts")
        }
    }
    
    // Create new post
    suspend fun createPost(post: Post): ApiResponse<Post> {
        return try {
            val newPost = api.createPost(post)
            ApiResponse.Success(newPost)
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "Failed to create post")
        }
    }

    // In Repository
    fun getPostsFlow(): Flow<ApiResponse<List<Post>>> = flow {
        emit(ApiResponse.Loading())
        try {
            val posts = apiService.getPosts()
            emit(ApiResponse.Success(posts))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

}