package com.binar.latihan_sharedpreferences

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.binar.latihan_sharedpreferences.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPrefLogin = "sharedPrefLogin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(sharedPrefLogin, MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val userLogin = true

            val editor = sharedPreferences.edit()
            editor.putString(NAME_KEY, name)
            editor.putString(PASSWORD_KEY, password)
            editor.putBoolean(USER_LOGIN_KEY, userLogin)
            editor.apply()

            navigateToHome()
            Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
        }

        if (isUserLoggedIn()) {
            navigateToHome()
        }
    }

    private fun isUserLoggedIn() : Boolean {
        return sharedPreferences.getBoolean(USER_LOGIN_KEY, false)
    }

    private fun navigateToHome() {
        val options= NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment,true)
            .build()
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment, null, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME_KEY = "name_key"
        const val PASSWORD_KEY = "password_key"
        const val USER_LOGIN_KEY = "user_login_key"
    }
}