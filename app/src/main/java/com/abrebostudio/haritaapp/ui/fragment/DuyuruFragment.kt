package com.abrebostudio.haritaapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.Duyuru
import com.abrebostudio.haritaapp.databinding.FragmentDuyuruBinding
import com.abrebostudio.haritaapp.ui.adapter.DuyuruAdapter
import com.abrebostudio.haritaapp.ui.viewmodel.DuyurularViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DuyuruFragment : Fragment() {
    private lateinit var binding:FragmentDuyuruBinding
    private lateinit var viewModel:DuyurularViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:DuyurularViewModel by viewModels()
        viewModel=temp

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentDuyuruBinding.inflate(inflater, container, false)

        viewModel.duyuru.observe(viewLifecycleOwner) {
            binding.progressBar3.visibility=View.VISIBLE
            val adapter:DuyuruAdapter= DuyuruAdapter(requireContext(),it,viewModel)
            binding.rvDuyurular.adapter=adapter
            binding.progressBar3.visibility=View.GONE
        }

        binding.searchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.araDuyuru(p0)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.araDuyuru(p0)
                }
                return true
            }

        })






        return binding.root
    }

}