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
import com.abrebostudio.haritaapp.data.model.IsPark
import com.abrebostudio.haritaapp.data.model.ParkItem
import com.abrebostudio.haritaapp.databinding.FragmentParkBinding
import com.abrebostudio.haritaapp.ui.viewmodel.ParkViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager


class ParkFragment : Fragment(),OnMapReadyCallback {
    private lateinit var binding:FragmentParkBinding
    private lateinit var viewModel:ParkViewModel
    private lateinit var mMap:GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var parkList:List<IsPark>
    private lateinit var ds:DatasourceMaps
    private lateinit var clusterManager: ClusterManager<ParkItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:ParkViewModel by viewModels()
        viewModel=temp
        ds=DatasourceMaps()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentParkBinding.inflate(inflater, container, false)


        return binding.root
    }

    @SuppressLint("MissingPermission", "PotentialBehaviorOverride")
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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView_is_park) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
    @SuppressLint("SuspiciousIndentation", "PotentialBehaviorOverride")
    private fun loadVisibleMarkers() {
        binding.progressBar4.visibility = View.VISIBLE
        val bounds = mMap.projection.visibleRegion.latLngBounds

        viewModel.parkList.observe(viewLifecycleOwner) { liste ->
            mMap.clear()
            parkList=liste
            clusterManager = ClusterManager(requireContext(), mMap)

            //mMap.setOnCameraIdleListener(clusterManager)
            //mMap.setOnMarkerClickListener(clusterManager)
            clusterManager.setOnClusterItemClickListener {
                val park=viewModel.toIsPark(it,parkList)
                if (park!=null){
                    viewModel.parkClicked(park,requireContext(),binding)
                }
                false
            }

            mMap.setOnMarkerClickListener {
                val park:IsPark=viewModel.getParkFromMarker(it,parkList)!!
                viewModel.parkClicked(park,requireContext(),binding)
                false
            }
            for (i in liste) {
                val loc = LatLng(i.lat.toDouble(), i.lng.toDouble())
                if (bounds.contains(loc)) {
                    val parkItem = ParkItem(i.lat, i.lng, i.parkName,i.lat)
                    clusterManager.addItem(parkItem)
                }
            }
            clusterManager.cluster()
            binding.progressBar4.visibility = View.GONE

        }
    }
}