package com.abrebostudio.haritaapp.data.datasource

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import com.abrebostudio.haritaapp.MainPageActivity
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.databinding.FragmentAyarlarListBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class DatasourceAyarlar() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun profilList():ArrayList<String>{
        val list=ArrayList<String>()
        list.add("Duyurular")
        list.add("Bildirilerim")
        list.add("Paylaş")
        list.add("Çıkış Yap")

        return list
    }

    fun iconSetup(x:String,binding: FragmentAyarlarListBinding){
        if (x=="Bildirilerim"){
            binding.imageViewProfilList.setImageResource(R.drawable.baseline_edit_note_24)

        }else if(x=="Paylaş"){
            binding.imageViewProfilList.setImageResource(R.drawable.baseline_share_24)
        }else if (x=="Çıkış Yap"){
            binding.imageViewProfilList.setImageResource(R.drawable.baseline_logout_24)
        }else if (x=="Duyurular"){
            binding.imageViewProfilList.setImageResource(R.drawable.baseline_notifications_24_black)
        }
    }

    fun ayarlarCliked(x:String,it: View,context: Context){
        if (x=="Bildirilerim"){
            Navigation.findNavController(it).navigate(R.id.action_ayarlarFragment_to_bildirilerimFragment)

        }else if (x=="Paylaş"){
            Snackbar.make(it,"Link Kopyalandı",Snackbar.LENGTH_SHORT).show()
        }else if (x=="Çıkış Yap"){
            logOut(context)

        }else if (x=="Duyurular"){
            Navigation.findNavController(it).navigate(R.id.action_ayarlarFragment_to_duyuruFragment)
        }
        else{
            Log.e("Mesaj","Ayarlar cliked hata")
        }
    }
    fun logOut(context: Context){
        auth.signOut()
        val intent:Intent=Intent(context,MainPageActivity::class.java)
        startActivity(context,intent,null)
    }
}