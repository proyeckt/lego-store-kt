package com.example.legostore_kt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.legostore_kt.R
import com.example.legostore_kt.domain.model.Product

class ProductAdapter (private val products: List<Product>, private val onClickListener: (Product) -> Unit ): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_product,parent,false))

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item: Product = products[position]
        holder.bind(item,onClickListener)
    }

    override fun getItemCount(): Int = products.size
}