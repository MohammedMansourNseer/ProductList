package com.dev.amazonProduct.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.amazonProduct.repository.AmazonProductRepository

class AmazonProductViewModelProviderFactory(
    val app: Application,
    private val amazonProductRepository: AmazonProductRepository
    ) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AmazonProductViewModel(app, amazonProductRepository) as T
    }
}