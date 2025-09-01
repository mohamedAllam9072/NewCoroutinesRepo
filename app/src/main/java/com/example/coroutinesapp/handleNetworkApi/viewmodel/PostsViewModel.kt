package com.example.coroutinesapp.handleNetworkApi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesapp.handleNetworkApi.data.model.ApiResponse
import com.example.coroutinesapp.handleNetworkApi.data.model.Post
import com.example.coroutinesapp.handleNetworkApi.repository.PostsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {
    private val repository = PostsRepository()
    private val _posts = MutableLiveData<ApiResponse<List<Post>>>(ApiResponse.Loading())
    val posts: MutableLiveData<ApiResponse<List<Post>>> = _posts

    private val _currentPost = MutableLiveData<ApiResponse<Post>>()
    val currentPost: MutableLiveData<ApiResponse<Post>> = _currentPost

    // Load all posts
    fun loadPosts() {
        viewModelScope.launch {
            _posts.value = ApiResponse.Loading()
            _posts.value = repository.getAllPosts()
        }
    }
    
    // Load specific post
    fun loadPost(id: Int) {
        viewModelScope.launch {
            _currentPost.value = ApiResponse.Loading()
            _currentPost.value = repository.getPostById(id)
        }
    }
    
    // Load posts by user
    fun loadUserPosts(userId: Int) {
        viewModelScope.launch {
            _posts.value = ApiResponse.Loading()
            _posts.value = repository.getPostsByUser(userId)
        }
    }
    
    // Create new post
    fun createNewPost(title: String, body: String, userId: Int = 1) {
        viewModelScope.launch {
            val newPost = Post(
                id = 0, // API will assign ID
                userId = userId,
                title = title,
                body = body
            )
            _currentPost.value = ApiResponse.Loading()
            _currentPost.value = repository.createPost(newPost)
        }
    }

    // In ViewModel
    fun loadPostsWithFlow() {
        viewModelScope.launch {
            repository.getPostsFlow()
                .catch { e ->
                    _posts.value = ApiResponse.Error(e.message ?: "Flow error")
                }
                .collect { response ->
                    _posts.value = response
                }
        }
    }
}