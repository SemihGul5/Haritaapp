package com.abrebostudio.haritaapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.model.IsPark
import com.abrebostudio.haritaapp.data.model.ParkItem
import com.abrebostudio.haritaapp.data.repo.Repository
import com.abrebostudio.haritaapp.databinding.FragmentBikeMapBinding
import com.abrebostudio.haritaapp.databinding.FragmentParkBinding
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParkViewModel:ViewModel() {

    var parkList= MutableLiveData<List<IsPark>>()
    var repository= Repository()

    init {
        parkYukle()
    }

    fun parkYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            val res=repository.parkUpload()
            parkList.value=res
        }
    }

    fun istanbulBounds(): LatLngBounds {
        return repository.istanbulBounds()
    }
    fun locationPermission(context: Context):Boolean {
        return repository.locationPermission(context)
    }
    fun getParkFromMarker(marker: Marker, liste:List<IsPark>): IsPark? = repository.getParkFromMarker(marker, liste)
    fun parkClicked(park: IsPark, context: Context, binding: FragmentParkBinding) = repository.parkClicked(park, context, binding)
    fun toIsPark(parkItem: ParkItem, list:List<IsPark>):IsPark? = repository.toIsPark(parkItem, list)





}