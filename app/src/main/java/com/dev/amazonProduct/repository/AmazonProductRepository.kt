package com.dev.amazonProduct.repository

import com.dev.amazonProduct.api.RetrofitInstance

class AmazonProductRepository() {

    suspend fun getAmazonProduct()= RetrofitInstance.api.getListProduct()
}