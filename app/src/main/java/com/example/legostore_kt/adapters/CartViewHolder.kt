package com.example.legostore_kt.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.legostore_kt.databinding.ItemCartBinding
import com.example.legostore_kt.domain.model.Product

class CartViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemCartBinding.bind(view)

    fun bind(product: Product, onClickDelete: (Int) -> Unit){
        binding.tvName.text = product.name
        binding.tvUnitPrice.text = "$${product.unit_price}"
        //Glide.with(binding.ivProduct.context).load()

        binding.ibDelete.setOnClickListener { onClickDelete(adapterPosition) }
    }
}