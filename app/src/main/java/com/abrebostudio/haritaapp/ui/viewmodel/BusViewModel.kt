package com.abrebostudio.haritaapp.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.model.Feature
import com.abrebostudio.haritaapp.data.repo.Repository
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.launch


class BusViewModel: ViewModel() {
    var bus= MutableLiveData<List<Feature>>()
    var repository= Repository()

    /*fun otobusYukle(mMap:GoogleMap, context: Context, featureList:List<Feature>){
        repository.otobusleriYukle(mMap, context, featureList)
    }*/
    init {
        otobusYukle()
    }

    fun otobusYukle(){
        viewModelScope.launch {
            val res=repository.busUpload()
            bus.value=res.features
        }
    }




}