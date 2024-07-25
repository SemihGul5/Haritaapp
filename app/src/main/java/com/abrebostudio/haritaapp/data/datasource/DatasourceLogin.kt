package com.abrebostudio.haritaapp.data.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.abrebostudio.haritaapp.data.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatasourceLogin {
    private var collectionUsers= Firebase.firestore.collection("Users")
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createUser(context: Context,user:User){
        auth.createUserWithEmailAndPassword(user.email!!,user.password!!).addOnCompleteListener {
            collectionUsers.document().set(user)
            Toast.makeText(context,"Başarılı",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun clearText (name:TextInputEditText, family:TextInputEditText, email:TextInputEditText, pass:TextInputEditText, pass2:TextInputEditText){
        name.setText("")
        family.setText("")
        email.setText("")
        pass.setText("")
        pass2.setText("")
    }





}