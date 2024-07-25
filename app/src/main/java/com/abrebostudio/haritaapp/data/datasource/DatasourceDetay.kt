package com.abrebostudio.haritaapp.data.datasource

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.model.Konum
import com.abrebostudio.haritaapp.ui.fragment.MapsFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class DatasourceDetay {
    private var collectionBildiri=Firebase.firestore.collection("Bildiriler")
    private var storage = FirebaseStorage.getInstance()
    private var storageReference = storage.reference
    private var firestore = FirebaseFirestore.getInstance()


    fun fotograf_izin(view:View,context: Context,activity: Activity,permissionLauncher:ActivityResultLauncher<String>,activityResultLauncher: ActivityResultLauncher<Intent>){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES)) {
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        } else {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }
    }


    fun uploadImageAndSaveData(imageUri: Uri,bildiri:Bildiri,context: Context){
        val ref = storageReference.child("images/" + UUID.randomUUID().toString())
        ref.putFile(imageUri).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri.toString()
                    bildiri.image=url
                    collectionBildiri.document().set(bildiri)
                    Toast.makeText(context,"Başarılı",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("Mesaj",e.message.toString())
            }
    }

    fun getDate(): String{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date()).toString()
        return currentDate
    }

    fun dateCastingAndUpdate(date:Date,id:String){
        val ts=Timestamp(date)
        val guncelle = HashMap<String,Any>()
        guncelle["tarih"] = ts
        collectionBildiri.document(id).update(guncelle)
    }

    @SuppressLint("SetTextI18n")
    fun textClearDetay(imageView: ImageView, radioGroup: RadioGroup, textBaslik:TextInputEditText, textAciklama:TextInputEditText){
        imageView.setImageResource(R.drawable.add_photo)
        radioGroup.clearCheck()
        textBaslik.setText("")
        textAciklama.setText("")
    }

    fun getRadioButtonTextDetay(radioGroup: RadioGroup): String{
        val selectedId=radioGroup.checkedRadioButtonId
        val selectedString = if (selectedId==R.id.radioButtonOneri) "Öneri" else "Şikayet"
        return selectedString
    }

}