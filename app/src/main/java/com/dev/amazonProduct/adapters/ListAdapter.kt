package com.dev.amazonProduct.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.amazonProduct.R
import com.dev.amazonProduct.models.Product
import kotlinx.android.synthetic.main.item_list_preview.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    private val differCallback= object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    val differ= AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_preview, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val product= differ.currentList[position]
        holder.itemView.apply {
            if(!product.image_urls.isNullOrEmpty())
                Glide.with(this).load(product.image_urls[0]).error(R.drawable.ic_no_image).placeholder(R.drawable.ic_camera)
                    .into(ivProductImage)


            tvProductName.text= product.name
            tvPrice.text= product.price
            setOnClickListener {
                onItemClickListener?.let { it(product) }
            }
        }
    }

    private var onItemClickListener:((Product)->Unit)?=null

    fun setOnItemClickListener(listener: (Product)->Unit){
        onItemClickListener= listener
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }
}