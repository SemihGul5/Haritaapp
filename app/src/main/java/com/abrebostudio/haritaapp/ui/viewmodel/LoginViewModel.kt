package com.abrebostudio.haritaapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.abrebostudio.haritaapp.data.model.User
import com.abrebostudio.haritaapp.data.repo.Repository
import com.google.android.material.textfield.TextInputEditText

class LoginViewModel: ViewModel() {
    var repository=Repository()


    fun createUser(context: Context, user: User) = repository.createUser(context, user)

    fun clearText (name: TextInputEditText, family: TextInputEditText, email: TextInputEditText, pass: TextInputEditText, pass2: TextInputEditText) = repository.clearText(name, family, email, pass, pass2)


}