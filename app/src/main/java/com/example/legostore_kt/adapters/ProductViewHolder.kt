package com.example.legostore_kt.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.legostore_kt.R
import com.example.legostore_kt.databinding.ItemProductBinding
import com.example.legostore_kt.domain.model.Product

class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemProductBinding.bind(view)

    //var listener: (()->Unit)? = null

    fun bind(product: Product, onClickListener: (Product) -> Unit, onInsertListener: (Product) -> Unit){
        val name ="Name :${product.name}"
        println(name)
        //binding.
        //Picasso.get().load(product.image).into(binding.ivProduct)
        Glide.with(binding.ivProduct.context)
            .load(product.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading_animated)
            .error(R.drawable.ic_no_image)
            .into(binding.ivProduct)

        itemView.setOnClickListener { onClickListener(product) }
        binding.ibAddCart.setOnClickListener{ onInsertListener(product) }
    }

}