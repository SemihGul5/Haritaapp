package com.abrebostudio.haritaapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.User
import com.abrebostudio.haritaapp.databinding.FragmentLoginBinding
import com.abrebostudio.haritaapp.ui.viewmodel.LoginViewModel



class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:LoginViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonKayTOl.setOnClickListener {
            binding.progressBarLogin.visibility=View.VISIBLE
            val name=binding.textEmail.text.toString()
            val family=binding.textPassword.text.toString()
            val email=binding.textEmail.text.toString()
            val password=binding.textPassword.text.toString()
            if (name!=""&&family!=""&&email!=""&&password!=""&&binding.textPasswordRe.text.toString()!=""){
                val user=User("",name,family,email,password)
                viewModel.createUser(requireContext(),user)
            }
            binding.progressBarLogin.visibility=View.GONE
            viewModel.clearText(binding.textName,binding.textFamily,binding.textEmail,binding.textPassword,binding.textPasswordRe)
        }


        return binding.root
    }

}