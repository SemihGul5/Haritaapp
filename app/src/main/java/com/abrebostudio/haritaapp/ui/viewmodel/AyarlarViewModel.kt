package com.abrebostudio.haritaapp.ui.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.abrebostudio.haritaapp.data.repo.Repository
import com.abrebostudio.haritaapp.databinding.FragmentAyarlarListBinding

class AyarlarViewModel: ViewModel() {
    var repository=Repository()
    var list=ArrayList<String>()


    init {
        profilList()
    }

    fun profilList():List<String>{
        list= repository.profilList()
        return list
    }
    fun iconSetup(x:String,binding: FragmentAyarlarListBinding){
        repository.iconSetup(x, binding)
    }
    fun ayarlarCliked(x:String,it: View) {
        repository.ayarlarCliked(x, it)
    }

}