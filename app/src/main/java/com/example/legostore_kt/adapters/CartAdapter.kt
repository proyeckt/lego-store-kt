package com.example.legostore_kt.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.legostore_kt.R
import com.example.legostore_kt.domain.model.Product

class CartAdapter (private val cartList: List<Product>,
                   private val onClickDelete: (Int) -> Unit) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_cart,parent,false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item: Product = cartList[position]
        holder.bind(item,onClickDelete)
    }

    override fun getItemCount(): Int = cartList.size
}