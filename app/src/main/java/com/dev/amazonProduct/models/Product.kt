package com.dev.amazonProduct.models

import java.io.Serializable


data class Product(
    var created_at: String?= "00/00/0000",
    val price: String?= "AED 0",
    val name: String? = "Not Found",
    val uid: String? = "0",
    val image_ids: ArrayList<String> = arrayListOf(),
    val image_urls: ArrayList<String> = arrayListOf(),
    val image_urls_thumbnails: ArrayList<String> = arrayListOf()

) : Serializable
