package com.example.legostore_kt.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.legostore_kt.R
import com.example.legostore_kt.adapters.ProductAdapter
import com.example.legostore_kt.api.APIService
import com.example.legostore_kt.api.ProductsResponse
import com.example.legostore_kt.databinding.FragmentHomeBinding
import com.example.legostore_kt.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter :ProductAdapter
    private val productImages = mutableListOf<Product>()

    val base_url = "https://489a19f7-f7d2-426a-8361-230148034a79.mock.pstmn.io/"

    //CREATE HTTP CLIENT
    private val okHttp = OkHttpClient.Builder()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        searchProducts()
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = ProductAdapter(productImages) {onItemSelected(it)} //{product -> onItemSelected(product)}
        binding.rvProducts.layoutManager = LinearLayoutManager(this.context)
        binding.rvProducts.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initObservers()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            ibLogout.setOnClickListener { handleLogout() }
            //ibLogout.setOnClickListener { searchProducts() }
        }
    }

    private fun handleLogout(){
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)

        if (preferences != null) {
            preferences.edit().remove("User").apply()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRetroFit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(okHttp.build())
            .build()
    }

    private fun searchProducts(){
        CoroutineScope(Dispatchers.IO).launch {

            val call: Response<ProductsResponse> = getRetroFit().create(APIService::class.java).getProducts()
            val products: ProductsResponse? = call.body()
            activity?.runOnUiThread {
                if(call.isSuccessful){
                    //show recycle view
                    val images: List<Product> = products?.products ?: emptyList()
                    productImages.clear()
                    productImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }
                else{
                    //show error
                    println("Bambini:${call.errorBody()}")
                    showError()
                }
            }

            /*
            //initiate the service
            val destinationService  = ServiceBuilder.buildService(APIService::class.java)
            val requestCall =destinationService.getProducts()

            requestCall.enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    Log.d("Response", "onResponse: ${response.body()}")
                    if (response.isSuccessful){
                        val countryList  = response.body()!!
                        Log.d("Response", "countrylist size : ${countryList.size}")
                        /*country_recycler.apply {
                            setHasFixedSize(true)
                            layoutManager = GridLayoutManager(this@MainActivity,2)
                            adapter = CountriesAdapter(response.body()!!)
                        }*/
                    }else{
                        println("Error 1")
                        Toast.makeText(context, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    println("Error 2")
                    Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
                }
            })
            */
        }
    }

    private fun onItemSelected(product: Product){
        Toast.makeText(this.context,product.name, Toast.LENGTH_SHORT).show()

        //val bundle = bundleOf("product" to product)
        //findNavController().navigate(R.id.action_homeFragment_to_productFragment,bundle)
    }

    private fun showError() {
        Toast.makeText(this.context,"An error happened loading data",Toast.LENGTH_SHORT).show()
    }
}