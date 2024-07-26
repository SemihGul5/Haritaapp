package com.abrebostudio.haritaapp.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.datasource.DatasourceMaps
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.model.Feature
import com.abrebostudio.haritaapp.databinding.FragmentBusMapBinding
import com.abrebostudio.haritaapp.ui.adapter.HatAdapter
import com.abrebostudio.haritaapp.ui.viewmodel.BusViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BusMapFragment : Fragment() {
    private lateinit var binding:FragmentBusMapBinding
    private lateinit var viewModel:BusViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var ds:DatasourceMaps
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:BusViewModel by viewModels()
        viewModel=temp
        ds=DatasourceMaps()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentBusMapBinding.inflate(inflater, container, false)



        viewModel.hat.observe(viewLifecycleOwner){
            binding.searchViewHat.setOnQueryTextListener(object :OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    val adapter=HatAdapter(requireContext(),it)
                    binding.rvHat.adapter=adapter
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    val adapter=HatAdapter(requireContext(),it)
                    binding.rvHat.adapter=adapter
                    return true
                }

            })
        }


        return binding.root
    }

    @SuppressLint("ServiceCast", "MissingPermission", "PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap=googleMap
        locationManager= requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!viewModel.locationPermission(requireContext())){
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            val sonKonum=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (sonKonum!=null){
                val konum= LatLng(sonKonum.latitude,sonKonum.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,15f))
                mMap.isMyLocationEnabled=true
                mMap.setOnCameraIdleListener {
                    //loadVisibleMarkers()
                }

                viewModel.getAllOtobusLocations("76")
                viewModel.otobus.observe(viewLifecycleOwner){
                    Log.e("Mesaj",it.toString())
                }

            }
        }

    }
    private fun loadVisibleMarkers() {
        binding.progressBar.visibility=View.VISIBLE
        val bounds = mMap.projection.visibleRegion.latLngBounds

        viewModel.bus.observe(viewLifecycleOwner) { liste ->
            liste?.let { listeFeatures ->
                mMap.clear()
                // Görünen alan içindeki markerları ekle
                for (feature in listeFeatures) {
                    val loc = LatLng(feature.properties.Enlem.toDouble(), feature.properties.Boylam.toDouble())
                    if (bounds.contains(loc)) {
                        mMap.addMarker(
                            MarkerOptions()
                                .position(loc)
                                .title(feature.properties.Plaka)
                        )
                    }
                }
                binding.progressBar.visibility=View.GONE
            }
        }
    }

}