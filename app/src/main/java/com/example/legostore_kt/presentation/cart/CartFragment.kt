package com.example.legostore_kt.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.legostore_kt.adapters.CartAdapter
import com.example.legostore_kt.databinding.FragmentCartBinding
import com.example.legostore_kt.util.Provider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(){

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var adapter :CartAdapter
    private val cartList = Provider.cartList


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater,container,false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this.context)
        val decoration = DividerItemDecoration(this.context,manager.orientation)

        adapter = CartAdapter(cartList) {onDeletedItem(it)} //{product -> onItemSelected(product)}
        binding.rvCart.layoutManager = manager
        binding.rvCart.adapter = adapter

        binding.rvCart.addItemDecoration(decoration)

        viewModel.calculateTotal(cartList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.cartListState.observe(viewLifecycleOwner) { state ->
            println("State:${state}")
            binding.tvTotal.text = "Total: $${state}"
        }
    }

    private fun initListeners() {
        with(binding) {
            ibBack.setOnClickListener {activity?.onBackPressed()}
            btBuy.setOnClickListener { handleBuy() }
        }
    }

    private fun handleBuy() {
        Toast.makeText(this.context,"Compra realizada",Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onDeletedItem(position: Int){
        cartList.removeAt(position)
        adapter.notifyItemRemoved(position)
        viewModel.calculateTotal(cartList)
    }

    /*private fun calculateTotal(): Int {
        return cartList.sumOf { it.unit_price }
    }*/

    /*
    private fun onInsertedItem(product: Product){
        println("Producto:${cartList.size.toString()}")
        var position = cartList.size -1
        cartList.add(index = position,product)
        adapter.notifyItemInserted(position)
        //manager.scrollToPositionWithOffset(position,10)
    }
    */
}