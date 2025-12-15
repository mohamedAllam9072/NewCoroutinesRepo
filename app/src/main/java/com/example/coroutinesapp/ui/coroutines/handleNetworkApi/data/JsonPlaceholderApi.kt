package com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data

import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.model.Post
import com.example.coroutinesapp.ui.coroutines.handleNetworkApi.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceholderApi {
    
    // GET all posts
    @GET("posts")
    suspend fun getPosts(): List<Post>
    
    // GET single post by ID
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post
    
    // GET posts by user ID
    @GET("posts")
    suspend fun getPostsByUser(@Query("userId") userId: Int): List<Post>
    
    // GET user by ID
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User
    
    // GET all users
    @GET("users")
    suspend fun getUsers(): List<User>
    
    // POST create new post
    @POST("posts")
    suspend fun createPost(@Body post: Post): Post
    
    // PUT update post
    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post
    
    // DELETE post
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}