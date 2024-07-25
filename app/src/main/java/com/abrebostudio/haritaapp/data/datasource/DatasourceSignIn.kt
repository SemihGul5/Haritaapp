package com.abrebostudio.haritaapp.data.datasource

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.abrebostudio.haritaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatasourceSignIn {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user=auth.currentUser

    fun signIn(email:String,password:String) :Boolean{
        var b=false
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            b=true
        }.addOnFailureListener {
            b=false
        }
        return b
    }

    fun oturumAcikMi(): Boolean{
        if (user==null){
            return false
        }else{
            return true
        }
    }







}