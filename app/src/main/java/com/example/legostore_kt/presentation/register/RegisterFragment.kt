package com.example.legostore_kt.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.legostore_kt.R
import com.example.legostore_kt.databinding.FragmentRegisterBinding
import com.example.legostore_kt.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(){
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            bRegister.setOnClickListener {
                handleRegister()
            }
            ibBack.setOnClickListener {activity?.onBackPressed()}
        }
    }

    private fun initObservers(){
        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            when(state){
                is Resource.Success -> {
                    handleLoading(isLoading = false)
                    Toast.makeText(
                        requireContext(),
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    activity?.onBackPressed()
                }
                is Resource.Error -> {
                    handleLoading(isLoading = false)
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> handleLoading(isLoading = true)
                else -> Unit
            }

        }
    }

    private fun handleRegister() {
        val email:String = binding.etEmail.text.toString()
        val password:String = binding.etPassword.text.toString()

        viewModel.register(email,password)
    }

    private fun handleLoading(isLoading: Boolean){
        with(binding){
            if(isLoading){
                bRegister.text = ""
                bRegister.isEnabled = false
                pbRegister.visibility = View.VISIBLE
            }
            else {
                pbRegister.visibility = View.GONE
                bRegister.text = getString(R.string.login_register_button)
                bRegister.isEnabled = true
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}