package com.dev.amazonProduct.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dev.amazonProduct.models.ProductResponse
import com.dev.amazonProduct.repository.AmazonProductRepository
import com.dev.amazonProduct.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class AmazonProductViewModel(
    app: android.app.Application,
    private val amazonProductRepository: AmazonProductRepository
) : AndroidViewModel(app){

    val amazonProduct: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()

    init {
        getAmazonProduct()
    }

    fun getAmazonProduct()= viewModelScope.launch {
        safeAmazonProductCall()
    }



    private fun handleAmazonProductResponse(response: Response<ProductResponse>): Resource<ProductResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return  Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeAmazonProductCall(){
        amazonProduct.postValue(Resource.Loading())
        try{
            if (hasInternetConnection()){
                val response= amazonProductRepository.getAmazonProduct()
                amazonProduct.postValue(handleAmazonProductResponse(response))
            }else{
                amazonProduct.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (t: Throwable){
            when(t){
                is IOException-> amazonProduct.postValue(Resource.Error("Network Failure"))
                else-> amazonProduct.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean{
        val connectivityManager= getApplication<com.dev.amazonProduct.Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork= connectivityManager.activeNetwork?: return false
        val capabilities= connectivityManager.getNetworkCapabilities(activeNetwork)?: return false

        return when{
            capabilities.hasTransport(TRANSPORT_WIFI)-> true
            capabilities.hasTransport(TRANSPORT_CELLULAR)-> true
            capabilities.hasTransport(TRANSPORT_ETHERNET)->true
            else -> false
        }
    }
}