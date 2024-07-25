package com.abrebostudio.haritaapp.ui.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.repo.Repository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MapsViewModel : ViewModel() {
    var repository=Repository()
    var bildiriList=MutableLiveData<List<Bildiri>>()


    init {
        bildiriYukle()
    }


    fun konumKaydet(tiklandiMi:Boolean, latitude:Double, longitude:Double, it: View, context: Context) = repository.konumKaydet(tiklandiMi,latitude,longitude,it,context)
    fun bildiriYukle(mMap: GoogleMap, context: Context) {
        repository.bildiriYukle(mMap, context)
    }
    fun bildiriYukle() : MutableLiveData<List<Bildiri>> {
        bildiriList=repository.bildiriYukle()
        return bildiriList
    }
    fun bildiriTiklanmasi(clickedBildiri: Bildiri,it:View) = repository.bildiriTiklanmasi(clickedBildiri, it)
    fun getBildiriFromMarker(marker: Marker): Bildiri? = repository.getBildiriFromMarker(marker)

}