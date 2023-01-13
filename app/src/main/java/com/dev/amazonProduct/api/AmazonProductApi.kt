package com.dev.amazonProduct.api

import com.dev.amazonProduct.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface AmazonProductApi {


    @GET("dynamodb-writer")
    suspend fun getListProduct(
    ):Response<ProductResponse> //return response


}