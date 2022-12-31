package com.example.legostore_kt.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.legostore_kt.MainActivity
import com.example.legostore_kt.R
import com.example.legostore_kt.databinding.FragmentLoginBinding
import com.example.legostore_kt.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(){

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSessionPreferences()
        loadThemePreferences()

        initObservers()
        initListeners()

    }

    private fun initObservers(){
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when(state){
                is Resource.Success -> {
                    handleLoading(isLoading = false)
                    /*
                    Toast.makeText(
                        requireContext(),
                        "User login successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    */
                    val email: String = binding.etEmail.text.toString()
                    saveSessionPreferences(email);
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

    private fun initListeners() {
        with(binding) {
            bLogin.setOnClickListener { handleLogin() }
            bRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
            swTheme.setOnClickListener { handleTheme(swTheme.isChecked) }
        }
    }

    private fun handleLogin() {
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()

        viewModel.login(email,password)
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                bLogin.text = ""
                bLogin.isEnabled = false
                bRegister.text = ""
                bRegister.isEnabled = false
                pbLogin.visibility = View.VISIBLE
            } else {
                pbLogin.visibility = View.GONE
                bLogin.text = getString(R.string.login_login_button)
                bLogin.isEnabled = true
                bRegister.text = getString(R.string.login_register_button)
                bRegister.isEnabled = true
            }
        }
    }

    private fun loadSessionPreferences() {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val userSaved = preferences?.getString("User", "")
        if(userSaved?.isNotEmpty() == true){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun saveSessionPreferences(user: String) {
        val preferences=activity?.getPreferences(Context.MODE_PRIVATE)
        if (preferences != null) {
            with(preferences.edit())
            {
                putString("User",user)
                apply()
            }
        }
    }

    private fun handleTheme(isChecked: Boolean){
        val ma = activity as MainActivity?

        val mode: Int = if (isChecked) 1 else 0
        saveThemePreferences(mode)
        ma?.setThemeApp()
    }

    private fun loadThemePreferences() {
        with(binding){
            val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
            val theme: Int? = preferences?.getInt("Theme", 1)

            if (theme == 1) {
                swTheme.isChecked = true
            } else {
                swTheme.isChecked = false
            }
        }

    }

    private fun saveThemePreferences(mode: Int) {
        val preferences=activity?.getPreferences(Context.MODE_PRIVATE)
        if (preferences != null) {
            with(preferences.edit())
            {
                putInt("Theme",mode)
                apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}