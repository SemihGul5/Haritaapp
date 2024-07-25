package com.abrebostudio.haritaapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.databinding.FragmentAnasayfaBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

class AnasayfaFragment : Fragment(), GoogleMap.OnMapClickListener, OnMapReadyCallback {
    private lateinit var binding:FragmentAnasayfaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentAnasayfaBinding.inflate(inflater, container, false)







        return binding.root
    }

    override fun onMapClick(p0: LatLng) {
        TODO("Not yet implemented")
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }


}