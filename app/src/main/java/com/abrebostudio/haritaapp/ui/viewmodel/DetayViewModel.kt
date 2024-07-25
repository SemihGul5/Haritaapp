package com.abrebostudio.haritaapp.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.repo.Repository
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DetayViewModel : ViewModel() {
    private var repository = Repository()

    fun fotograf_izin(view: View, context: Context, activity: Activity, permissionLauncher: ActivityResultLauncher<String>, activityResultLauncher: ActivityResultLauncher<Intent>) =
        repository.fotograf_izin(view,context,activity,permissionLauncher,activityResultLauncher)
    fun uploadImageAndSaveData(imageUri: Uri,bildiri:Bildiri,context: Context) = repository.uploadImageAndSaveData(imageUri, bildiri,context)
    fun getDate(): String = repository.getDate()
    fun textClearDetay(imageView: ImageView, radioGroup: RadioGroup, textBaslik: TextInputEditText, textAciklama: TextInputEditText)= repository.textClearDetay(imageView, radioGroup, textBaslik, textAciklama)
    fun getRadioButtonTextDetay(radioGroup: RadioGroup): String = repository.getRadioButtonTextDetay(radioGroup)




}