package com.example.legostore_kt.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.legostore_kt.databinding.FragmentProductBinding
import com.example.legostore_kt.domain.model.Product
import com.example.legostore_kt.util.Provider
import com.squareup.picasso.Picasso

class ProductFragment: Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var product: Product

    private val cartList = Provider.cartList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            product = bundle.getParcelable("product")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProductName.text = product.name
        binding.tvUnitPrice.text = product.unit_price.toString()
        binding.tvStock.text = product.stock.toString()
        Picasso.get().load(product.image).into(binding.ivImage)

        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            btAddCart.setOnClickListener { addToCart(product) }
            ibBack.setOnClickListener {activity?.onBackPressed()}
        }
    }

    private fun addToCart(product: Product){
        val msg: String = if(product.stock > 0){
            cartList.add(cartList.size,product)
            "Product ${product.name} has been added sucessfully"
        } else "Error: Product ${product.name} doesn't have enough stock"
        Toast.makeText(this.context,msg, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}