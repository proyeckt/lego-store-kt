package com.example.legostore_kt.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.legostore_kt.databinding.FragmentProductBinding
import com.example.legostore_kt.domain.model.Product
import com.squareup.picasso.Picasso

class ProductFragment: Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var product: Product

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
            btAddCart.setOnClickListener { addToCart() }
            ibBack.setOnClickListener {activity?.onBackPressed()}
        }
    }

    fun addToCart () {
        Toast.makeText(this.context, "Product ${product.name} has been added to the cart",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}