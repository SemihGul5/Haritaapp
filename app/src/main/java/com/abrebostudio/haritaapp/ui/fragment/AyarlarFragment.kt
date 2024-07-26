package com.abrebostudio.haritaapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.abrebostudio.haritaapp.databinding.FragmentAyarlarBinding
import com.abrebostudio.haritaapp.ui.adapter.AyarlarAdapter
import com.abrebostudio.haritaapp.ui.viewmodel.AyarlarViewModel



class AyarlarFragment : Fragment() {
    private lateinit var binding:FragmentAyarlarBinding
    private lateinit var viewModel:AyarlarViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:AyarlarViewModel by viewModels()
        viewModel=temp

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentAyarlarBinding.inflate(inflater, container, false)
        val list = viewModel.profilList()
        val adapter=AyarlarAdapter(requireContext(),list,viewModel)

        binding.rvProfil.adapter=adapter


        return binding.root
    }


}