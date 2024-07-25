package com.abrebostudio.haritaapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.abrebostudio.haritaapp.MainPageActivity
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.databinding.FragmentSignInBinding
import com.abrebostudio.haritaapp.ui.viewmodel.SignInViewModel



class SignInFragment : Fragment() {
    private lateinit var binding:FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:SignInViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignInBinding.inflate(inflater, container, false)


        binding.buttonGirisYap.setOnClickListener {
            val email=binding.textEmail.text.toString()
            val password=binding.textPassword.text.toString()
            if (email!=""&&password!=""){
                viewModel.signIn(email,password)
                val intent:Intent=Intent(requireContext(),MainPageActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"E-mail ve şifreyi giriniz",Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.oturumAcikMi()){
            val intent:Intent=Intent(requireContext(),MainPageActivity::class.java)
            startActivity(intent)
        }
    }

}