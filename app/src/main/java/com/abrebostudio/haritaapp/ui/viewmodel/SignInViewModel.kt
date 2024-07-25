package com.abrebostudio.haritaapp.ui.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.abrebostudio.haritaapp.data.repo.Repository

class SignInViewModel:ViewModel() {

    var repository=Repository()

    fun signIn(email:String,password:String) = repository.signIn(email, password)
    fun oturumAcikMi(): Boolean = repository.oturumAcikMi()









}