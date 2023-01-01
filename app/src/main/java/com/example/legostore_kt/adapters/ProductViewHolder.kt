package com.example.legostore_kt.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.legostore_kt.databinding.ItemProductBinding
import com.example.legostore_kt.domain.model.Product
import com.squareup.picasso.Picasso

class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemProductBinding.bind(view)

    fun bind(product: Product, onClickListener: (Product) -> Unit){
        val name ="Name :${product.name}"
        println(name)
        //binding.
        Picasso.get().load(product.image).into(binding.ivProduct)
        //Glide.with(binding.ivProduct.context).load()

        itemView.setOnClickListener {  }
        binding.ivProduct.setOnClickListener { onClickListener(product) }
    }

}