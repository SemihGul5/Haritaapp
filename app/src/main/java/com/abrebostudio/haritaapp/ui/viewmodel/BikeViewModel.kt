package com.abrebostudio.haritaapp.ui.viewmodel


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.repo.Repository
import com.abrebostudio.haritaapp.databinding.FragmentBikeMapBinding
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.launch


class BikeViewModel:ViewModel() {
    var bike= MutableLiveData<List<Datalist>>()
    var repository= Repository()

    init {
        bikeYukle()
    }

    fun bikeYukle(){
        viewModelScope.launch {
            val res=repository.bikeUpload()

            bike.value=res.dataList
        }
    }
    fun getBikeFromMarker(marker: Marker, liste:List<Datalist>): Datalist? = repository.getBikeFromMarker(marker, liste)
    fun bikeClicked(bike:Datalist, context: Context, binding: FragmentBikeMapBinding) = repository.bikeClicked(bike, context, binding)

    fun istanbulBounds(): LatLngBounds {
        return repository.istanbulBounds()
    }
    fun locationPermission(context: Context):Boolean {
        return repository.locationPermission(context)
    }
}