package com.example.coroutinesapp.ui

import PostAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.coroutinesapp.databinding.FragmentPostsBinding
import com.example.coroutinesapp.handleNetworkApi.data.model.ApiResponse
import com.example.coroutinesapp.handleNetworkApi.data.model.Post
import com.example.coroutinesapp.handleNetworkApi.viewmodel.PostsViewModel
import kotlin.getValue

class PostsFragment : Fragment() {
    private lateinit var binding: FragmentPostsBinding
    private val viewModel: PostsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        setupObservers()
        setupClickListeners()
        // Load initial data
        viewModel.loadPosts()
        return binding.root
    }
    private fun setupObservers() {
        // Observe posts list
        viewModel.posts.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    showLoading(false)
                    displayPosts(response.data)
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    showError(response.message)
                }
            }
        }

        // Observe single post
        viewModel.currentPost.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    Log.d("TAG11111", "setupObservers:${response.data} ")
                    //   displayPostDetails(response.data)
                }
                is ApiResponse.Error -> {
                    showError(response.message)
                }
                else -> {}
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnRefresh.setOnClickListener {
            viewModel.loadPosts()
        }

        binding.btnCreatePost.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()

            if (title.isNotEmpty() && body.isNotEmpty()) {
                viewModel.createNewPost(title, body)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayPosts(posts: List<Post>) {
        val adapter = PostAdapter(posts) { post ->
            // Click listener - load specific post
            viewModel.loadPost(post.id)
        }
        binding.recyclerView.adapter = adapter
    }

//    private fun displayPostDetails(post: Post) {
//        binding.tvPostTitle.text = post.title
//        binding.tvPostBody.text = post.body
//    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_LONG).show()
    }

}