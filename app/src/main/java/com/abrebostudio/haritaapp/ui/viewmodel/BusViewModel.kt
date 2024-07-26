package com.abrebostudio.haritaapp.ui.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.model.Feature
import com.abrebostudio.haritaapp.data.model.Hat
import com.abrebostudio.haritaapp.data.model.Otobus
import com.abrebostudio.haritaapp.data.repo.Repository
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call


class BusViewModel: ViewModel() {
    var bus= MutableLiveData<List<Feature>>()
    var repository= Repository()
    var hat=MutableLiveData<List<Hat>>()
    var otobus=MutableLiveData<List<Otobus>>()

    init {
        otobusYukle()
        hatUpload()
    }

    fun otobusYukle(){
        viewModelScope.launch {
            val res=repository.busUpload()
            bus.value=res.features
        }
    }

    fun locationPermission(context: Context):Boolean{
        return repository.locationPermission(context)
    }
    fun hatUpload(){
        CoroutineScope(Dispatchers.Main).launch{
            val res=repository.hatUpload()
            hat.value=res
        }
    }

    fun getAllOtobusLocations(hatKodu: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                otobus.value = repository.getAllOtobusLocations(hatKodu)
            } catch (e: Exception) {
                Log.e("Mesaj", e.message.toString())
            }
        }
    }
}