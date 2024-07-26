package com.abrebostudio.haritaapp.data.repo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import com.abrebostudio.haritaapp.data.datasource.DatasourceAyarlar
import com.abrebostudio.haritaapp.data.datasource.DatasourceDetay
import com.abrebostudio.haritaapp.data.datasource.DatasourceLogin
import com.abrebostudio.haritaapp.data.datasource.DatasourceMaps
import com.abrebostudio.haritaapp.data.datasource.DatasourceSignIn
import com.abrebostudio.haritaapp.data.model.BikeCollection
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.model.Duyuru
import com.abrebostudio.haritaapp.data.model.Feature
import com.abrebostudio.haritaapp.data.model.FeatureCollection
import com.abrebostudio.haritaapp.data.model.Hat
import com.abrebostudio.haritaapp.data.model.IsPark
import com.abrebostudio.haritaapp.data.model.Otobus
import com.abrebostudio.haritaapp.data.model.ParkItem
import com.abrebostudio.haritaapp.data.model.User
import com.abrebostudio.haritaapp.databinding.FragmentAyarlarListBinding
import com.abrebostudio.haritaapp.databinding.FragmentBikeMapBinding
import com.abrebostudio.haritaapp.databinding.FragmentParkBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call

class Repository {
    private var datasourceDetay=DatasourceDetay()
    private var datasourceLogin=DatasourceLogin()
    private var datasourceMaps=DatasourceMaps()
    private var datasourceSignIn=DatasourceSignIn()
    private var datasourceAyarlar=DatasourceAyarlar()


    fun konumKaydet(tiklandiMi:Boolean, latitude:Double, longitude:Double, it: View, context: Context) = datasourceMaps.konumKaydet(tiklandiMi,latitude,longitude,it,context)
    fun fotograf_izin(view:View, context: Context, activity: Activity, permissionLauncher: ActivityResultLauncher<String>, activityResultLauncher: ActivityResultLauncher<Intent>) =
        datasourceDetay.fotograf_izin(view,context,activity,permissionLauncher,activityResultLauncher)

    fun uploadImageAndSaveData(imageUri: Uri,bildiri:Bildiri,context: Context) = datasourceDetay.uploadImageAndSaveData(imageUri, bildiri,context)
    fun getDate(): String = datasourceDetay.getDate()
    fun textClearDetay(imageView: ImageView, radioGroup: RadioGroup, textBaslik: TextInputEditText, textAciklama: TextInputEditText) = datasourceDetay.textClearDetay(imageView, radioGroup, textBaslik, textAciklama)
    fun getRadioButtonTextDetay(radioGroup: RadioGroup): String = datasourceDetay.getRadioButtonTextDetay(radioGroup)
    fun createUser(context: Context,user: User) = datasourceLogin.createUser(context, user)
    fun clearText (name:TextInputEditText, family:TextInputEditText, email:TextInputEditText, pass:TextInputEditText, pass2:TextInputEditText) = datasourceLogin.clearText(name, family, email, pass, pass2)
    fun signIn(email:String,password:String) = datasourceSignIn.signIn(email, password)
    fun oturumAcikMi(): Boolean = datasourceSignIn.oturumAcikMi()
    fun bildiriYukle(mMap: GoogleMap, context: Context) = datasourceMaps.bildiriYukle(mMap, context)
    fun bildiriTiklanmasi(clickedBildiri: Bildiri,it:View) = datasourceMaps.bildiriTiklanmasi(clickedBildiri, it)
    fun getBildiriFromMarker(marker: Marker): Bildiri? = datasourceMaps.getBildiriFromMarker(marker)
    fun bildiriYukle() : MutableLiveData<List<Bildiri>> = datasourceMaps.bildiriYukle()
    fun profilList():ArrayList<String> = datasourceAyarlar.profilList()
    fun iconSetup(x:String,binding: FragmentAyarlarListBinding) = datasourceAyarlar.iconSetup(x, binding)
    fun ayarlarCliked(x:String,it: View,context: Context) = datasourceAyarlar.ayarlarCliked(x, it,context)
    suspend fun busUpload(): FeatureCollection = datasourceMaps.busUpload()
    suspend fun bikeUpload(): BikeCollection = datasourceMaps.bikeUpload()
    suspend fun duyuruUpload(): List<Duyuru> = datasourceMaps.duyuruUpload()
    suspend fun parkUpload(): List<IsPark> = datasourceMaps.parkUpload()
    suspend fun hatUpload(): List<Hat> = datasourceMaps.hatUpload()
    suspend fun getAllOtobusLocations(hatKodu: String): List<Otobus> = datasourceMaps.getAllOtobusLocations(hatKodu)
    suspend fun duyuruAraUpload(kelime:String): List<Duyuru> = datasourceMaps.duyuruAraUpload(kelime)
    fun getBikeFromMarker(marker: Marker,liste:List<Datalist>): Datalist? = datasourceMaps.getBikeFromMarker(marker, liste)
    fun getParkFromMarker(marker: Marker,liste:List<IsPark>): IsPark? = datasourceMaps.getParkFromMarker(marker, liste)
    fun parkClicked(park: IsPark, context:Context, binding: FragmentParkBinding) = datasourceMaps.parkClicked(park, context, binding)
    fun bikeClicked(bike:Datalist,context:Context,binding: FragmentBikeMapBinding) = datasourceMaps.bikeClicked(bike, context, binding)
    fun istanbulBounds(): LatLngBounds = datasourceMaps.istanbulBounds()
    fun locationPermission(context: Context):Boolean = datasourceMaps.locationPermission(context)
    fun toIsPark(parkItem: ParkItem, list:List<IsPark>):IsPark? = datasourceMaps.toIsPark(parkItem, list)
}