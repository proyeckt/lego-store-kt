package com.example.legostore_kt.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.legostore_kt.R
import com.example.legostore_kt.adapters.CartAdapter
import com.example.legostore_kt.api.APIService
import com.example.legostore_kt.api.ProductsResponse
import com.example.legostore_kt.databinding.FragmentCartBinding
import com.example.legostore_kt.domain.model.Product
import com.example.legostore_kt.services.ServiceBuilder
import com.example.legostore_kt.util.Provider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class CartFragment : Fragment(){

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var adapter :CartAdapter
    private lateinit var cartList : MutableList<Product>
    private lateinit var productsList : MutableList<Product>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater,container,false)

        cartList = Provider.cartList
        productsList = Provider.productsList

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
        Toast.makeText(this.context,"Purchase done",Toast.LENGTH_LONG).show()

        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

            val service = ServiceBuilder.buildService(APIService::class.java)
            val call: Response<ProductsResponse> = service.buy()

            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    val products: ProductsResponse? = call.body()
                    //show recycle view
                    val productsRetrieved: List<Product> = products?.products ?: emptyList()
                    productsList.clear()
                    productsList.addAll(productsRetrieved)

                    cartList.clear()
                    adapter.notifyDataSetChanged()
                    viewModel.calculateTotal(cartList)
                    findNavController().navigate(R.id.action_cartFragment_to_homeFragment)

                } else {
                    //show error
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this.context,"An error happened loading data",Toast.LENGTH_SHORT).show()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
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