package com.abrebostudio.haritaapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrebostudio.haritaapp.data.model.Duyuru
import com.abrebostudio.haritaapp.data.repo.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DuyurularViewModel:ViewModel() {

    var duyuru= MutableLiveData<List<Duyuru>>()
    var repository= Repository()

    init {
        duyuruYukle()
    }

    fun duyuruYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            val res=repository.duyuruUpload()
            duyuru.value=res
        }
    }

    fun araDuyuru(kelime:String){
        CoroutineScope(Dispatchers.Main).launch {
            duyuru.value=repository.duyuruAraUpload(kelime)
        }
    }
}