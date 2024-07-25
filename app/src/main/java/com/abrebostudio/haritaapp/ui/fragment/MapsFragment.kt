package com.abrebostudio.haritaapp.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.databinding.FragmentMapsBinding
import com.abrebostudio.haritaapp.ui.adapter.BildiriAdapter
import com.abrebostudio.haritaapp.ui.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MapsFragment : Fragment(), GoogleMap.OnMapClickListener {
    private lateinit var binding:FragmentMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var latitude: Double? =null
    private var longitude: Double? =null
    private lateinit var viewModel:MapsViewModel
    private var tiklandiMi:Boolean?=null

    @SuppressLint("ServiceCast", "MissingPermission", "PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap
        mMap.setOnMapClickListener(this)
        locationManager= requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        backPress()
        // interface tanımlarında object: kullanılmalı, javadaki new yerine object: kullanılır gibi düşün
        locationListener= object : LocationListener {
            override fun onLocationChanged(p0: Location) {
            }
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
            val sonKonum=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (sonKonum!=null){
                val konum=LatLng(sonKonum.latitude,sonKonum.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f))
                mMap.isMyLocationEnabled=true
                mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
                viewModel.bildiriYukle(mMap,requireContext())

            }
        }

        mMap.setOnMarkerClickListener { marker ->
            val clickedBildiri: Bildiri = viewModel.getBildiriFromMarker(marker)!!
            Toast.makeText(context, clickedBildiri.title,Toast.LENGTH_SHORT).show()
            viewModel.bildiriTiklanmasi(clickedBildiri,binding.root)
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()

        val temp:MapsViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding=FragmentMapsBinding.inflate(inflater, container, false)

        latitude=0.0
        longitude=0.0
        tiklandiMi=false


        binding.buttonKonumKaydet.setOnClickListener {
            viewModel.konumKaydet(tiklandiMi!!,latitude!!,longitude!!,it,requireContext())
        }

        binding.toolbar2.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.maps_fragment_list_view -> {
                    val fragment=childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    fragment?.view?.visibility=View.GONE
                    binding.buttonKonumKaydet.visibility=View.GONE
                    binding.rvMaps.visibility=View.VISIBLE
                    viewModel.bildiriList.observe(viewLifecycleOwner){
                        val bildiriAdapter = BildiriAdapter(requireContext(),it,viewModel)
                        binding.rvMaps.adapter=bildiriAdapter
                    }
                    true
                }
                R.id.maps_fragment_map_view->{
                    val fragment=childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    fragment?.view?.visibility=View.VISIBLE
                    binding.buttonKonumKaydet.visibility=View.VISIBLE
                    binding.rvMaps.visibility=View.GONE
                    true
                }
                else -> false
            }
        }








        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onResume() {
        super.onResume()
    }



    override fun onMapClick(p0: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        latitude=p0.latitude
        longitude=p0.longitude
        tiklandiMi=true
    }
    private fun registerLauncher(){
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){ result->
            if (result){
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
                }
            }else{
                Toast.makeText(requireContext(),"izin verilmedi",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun backPress(){
        val backButtonCallback = object : OnBackPressedCallback(true) {
            private var backPressedTime: Long = 0

            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (backPressedTime + 2000 > currentTime) {
                    requireActivity().finishAffinity()
                } else {
                    Toast.makeText(context, "Çıkmak için tekrar basın", Toast.LENGTH_SHORT).show()
                }
                backPressedTime = currentTime
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backButtonCallback)

    }
}