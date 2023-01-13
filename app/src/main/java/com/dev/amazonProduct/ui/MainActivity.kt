package com.dev.amazonProduct.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dev.amazonProduct.R
import com.dev.amazonProduct.repository.AmazonProductRepository

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: AmazonProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository= AmazonProductRepository()
        val viewModelProviderFactory= AmazonProductViewModelProviderFactory(application, repository)
        viewModel= ViewModelProvider(this, viewModelProviderFactory).get(AmazonProductViewModel::class.java)
    }
}
