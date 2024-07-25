package com.abrebostudio.haritaapp.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.viewModels
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.datasource.DatasourceMaps
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.databinding.FragmentBikeMapBinding
import com.abrebostudio.haritaapp.ui.viewmodel.BikeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BikeMapFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding:FragmentBikeMapBinding
    private lateinit var viewModel:BikeViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var bikeList: List<Datalist>
    private lateinit var ds:DatasourceMaps


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:BikeViewModel by viewModels()
        viewModel=temp
        ds=DatasourceMaps()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapViewBike) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentBikeMapBinding.inflate(inflater, container, false)


        return binding.root
    }

    @SuppressLint("PotentialBehaviorOverride", "MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        mMap=p0
        locationManager= requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!viewModel.locationPermission(requireContext())){
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            val sonKonum=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (sonKonum!=null){
                //val konum= LatLng(sonKonum.latitude,sonKonum.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viewModel.istanbulBounds().center,13f))
                mMap.isMyLocationEnabled=true
                //mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE

                mMap.setOnCameraIdleListener {
                    loadVisibleMarkers()
                }
            }
        }


        mMap.setOnMarkerClickListener {
            val bike: Datalist = viewModel.getBikeFromMarker(it,bikeList)!!
            viewModel.bikeClicked(bike,requireContext(),binding)
            false
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun loadVisibleMarkers() {
        binding.progressBar2.visibility=View.VISIBLE
        val bounds = mMap.projection.visibleRegion.latLngBounds

        viewModel.bike.observe(viewLifecycleOwner) { liste ->
            bikeList=liste
                mMap.clear()
                for (i in liste) {
                    if (i.lat==""){
                        continue
                    }
                    if (i.lon==""){
                        continue
                    }
                    val loc = LatLng(i.lat.toDouble(),i.lon.toDouble())
                    if (bounds.contains(loc)) {
                        mMap.addMarker(
                            MarkerOptions()
                                .position(loc)
                                .title(i.adi)
                                .icon(BitmapDescriptorFactory.fromBitmap(ds.generateSmallIcon(requireContext(),R.drawable.bike,100,100)))
                        )
                    }
                }
                binding.progressBar2.visibility=View.GONE
        }
    }

}