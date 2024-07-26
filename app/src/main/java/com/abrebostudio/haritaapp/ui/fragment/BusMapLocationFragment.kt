package com.abrebostudio.haritaapp.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.viewModels
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.datasource.DatasourceMaps
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.model.Otobus
import com.abrebostudio.haritaapp.databinding.FragmentBusMapLocationBinding
import com.abrebostudio.haritaapp.ui.viewmodel.BusViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BusMapLocationFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding:FragmentBusMapLocationBinding
    private lateinit var viewModel:BusViewModel
    private var otobus:Otobus? = null
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var otobusList: List<Otobus>
    private lateinit var ds: DatasourceMaps
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:BusViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBusMapLocationBinding.inflate(inflater, container, false)

        val bundle=BusMapLocationFragmentArgs.fromBundle(requireArguments())
        val hat=bundle.hat

        viewModel.getAllOtobusLocations(hat.SHATKODU)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapViewOtobus) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
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
                val konum= LatLng(sonKonum.latitude,sonKonum.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum,13f))
                mMap.isMyLocationEnabled=true
                //mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE

                loadVisibleMarkers()

            }
        }


        mMap.setOnMarkerClickListener {
            /*val bike: Datalist = viewModel.getBikeFromMarker(it,bikeList)!!
            viewModel.bikeClicked(bike,requireContext(),binding)*/
            false
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun loadVisibleMarkers() {
        binding.progressBar5.visibility=View.VISIBLE
        val bounds = mMap.projection.visibleRegion.latLngBounds

        viewModel.otobus.observe(viewLifecycleOwner) { liste ->
            otobusList=liste
            mMap.clear()
            for (i in liste) {
                val loc = LatLng(i.enlem.toDouble(),i.boylam.toDouble())
                if (bounds.contains(loc)) {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(loc)
                            .title(i.hatkodu)

                    )
                }
            }
            binding.progressBar5.visibility=View.GONE
        }
    }

}