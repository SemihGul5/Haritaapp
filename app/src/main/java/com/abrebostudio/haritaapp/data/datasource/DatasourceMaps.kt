package com.abrebostudio.haritaapp.data.datasource

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.BikeCollection
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.model.Datalist
import com.abrebostudio.haritaapp.data.model.Duyuru
import com.abrebostudio.haritaapp.data.model.FeatureCollection
import com.abrebostudio.haritaapp.data.model.IsPark
import com.abrebostudio.haritaapp.data.model.Konum
import com.abrebostudio.haritaapp.data.model.ParkItem
import com.abrebostudio.haritaapp.databinding.FragmentBikeMapBinding
import com.abrebostudio.haritaapp.databinding.FragmentParkBinding
import com.abrebostudio.haritaapp.retrofit.ApiUtils
import com.abrebostudio.haritaapp.retrofit.MekansalDao
import com.abrebostudio.haritaapp.ui.fragment.MapsFragmentDirections
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatasourceMaps:MekansalDao {
    var bildiriList=MutableLiveData<List<Bildiri>>()
    private var collectionBildiri= Firebase.firestore.collection("Bildiriler")
    val liste = ArrayList<Bildiri>()
    val liste2=ArrayList<Bildiri>()


    override suspend fun busUpload(): FeatureCollection {
        return ApiUtils.getMekansalDao().busUpload()
    }

    override suspend fun bikeUpload(): BikeCollection {
        return ApiUtils.getBikeDao().bikeUpload()
    }

    override suspend fun duyuruUpload(): List<Duyuru> =
        withContext(Dispatchers.IO){
            return@withContext ApiUtils.getDuyuruDao().duyuruUpload()
        }

    override suspend fun duyuruAraUpload(kelime: String): List<Duyuru> =
        withContext(Dispatchers.IO){
            return@withContext ApiUtils.getAraDuyuruDao().duyuruAraUpload(kelime)
        }

    override suspend fun parkUpload(): List<IsPark> {
        return ApiUtils.getParkDao().parkUpload()
    }

    fun getBikeFromMarker(marker: Marker,liste:List<Datalist>): Datalist? {
        val markerPosition = marker.position
        for (bike in liste) {
            if (bike.lat==""){
                continue
            }
            if (bike.lon==""){
                continue
            }
            val bikeLatitude: Double = bike.lat.toDouble()
            val bikeLongitude: Double = bike.lon.toDouble()
            val bikeLocation = LatLng(bikeLatitude, bikeLongitude)
            if (bikeLocation == markerPosition) {
                return bike
            }
        }
        return null
    }

    fun bikeClicked(bike: Datalist, context:Context, binding: FragmentBikeMapBinding){
        val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context,R.style.BottomSheetDialogTheme)
        val bottomSheetView=
            LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog,binding.root.findViewById<ScrollView>(R.id.bottom_sheet_layout))

        bottomSheetView.findViewById<TextView>(R.id.bottom_isbike_ad).text=bike.adi
        bottomSheetView.findViewById<TextView>(R.id.bottom_bos).text=bike.bos
        bottomSheetView.findViewById<TextView>(R.id.bottom_dolu).text=bike.dolu
        bottomSheetView.findViewById<TextView>(R.id.bottom_istasyon_no).text=bike.istasyon_no

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()


    }
    fun getParkFromMarker(marker: Marker,liste:List<IsPark>): IsPark? {
        val markerPosition = marker.position
        for (park in liste) {
            val bikeLatitude: Double = park.lat.toDouble()
            val bikeLongitude: Double = park.lng.toDouble()
            val bikeLocation = LatLng(bikeLatitude, bikeLongitude)
            if (bikeLocation == markerPosition) {
                return park
            }
        }
        return null
    }

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    fun parkClicked(park: IsPark, context:Context, binding: FragmentParkBinding){
        val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context,R.style.BottomSheetDialogTheme)
        val bottomSheetView= LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog_park,binding.root.findViewById<ScrollView>(R.id.bottom_sheet_layout))

        bottomSheetView.findViewById<TextView>(R.id.bottom_park_ad).text=park.parkName
        bottomSheetView.findViewById<TextView>(R.id.bottom_kapasite).text=park.capacity.toString()
        bottomSheetView.findViewById<TextView>(R.id.bottom_bos).text=park.emptyCapacity.toString()
        bottomSheetView.findViewById<TextView>(R.id.bottom_calisma_saati).text=park.workHours
        bottomSheetView.findViewById<TextView>(R.id.bottom_park_tip).text=park.parkType
        bottomSheetView.findViewById<TextView>(R.id.bottom_ucretsiz_dk).text=park.freeTime.toString()
        bottomSheetView.findViewById<TextView>(R.id.bottom_park_semt).text=park.district
        if (park.isOpen==1){
            bottomSheetView.findViewById<TextView>(R.id.bottom_acik_mi).text="Açık"
        }else{
            bottomSheetView.findViewById<TextView>(R.id.bottom_acik_mi).text="Kapalı"

        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    fun istanbulBounds():LatLngBounds{
        val istanbulBounds = LatLngBounds(
            LatLng(40.8024, 28.5449),
            LatLng(41.2314, 29.3783)
        )
        return istanbulBounds
    }
    fun locationPermission(context: Context):Boolean{
        var b=false
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            b=false
        }else{
            b=true
        }
        return b
    }

    fun bildiriYukle(mMap:GoogleMap,context: Context) {
        collectionBildiri.addSnapshotListener { value, error ->
            if(value != null){
                for(d in value.documents){
                    val bildiri = d.toObject(Bildiri::class.java)
                    if(bildiri != null){
                        bildiri.id = d.id
                        liste.add(bildiri)

                        val bildiriLocation = LatLng(bildiri.enlem, bildiri.boylam)
                        if (bildiri.type=="Öneri"){
                            mMap.addMarker(MarkerOptions()
                                .position(bildiriLocation)
                                .icon(BitmapDescriptorFactory.fromBitmap(generateSmallIcon(context,R.drawable.oneri_marker,100,100))))
                        }else{
                             mMap.addMarker(MarkerOptions()
                                .position(bildiriLocation)
                                .icon(BitmapDescriptorFactory.fromBitmap(generateSmallIcon(context,R.drawable.sikayet_markr,100,100))))
                        }
                    }
                }
            }

        }
    }
    fun bildiriYukle() : MutableLiveData<List<Bildiri>> {
        collectionBildiri.addSnapshotListener { value, error ->
            if(value != null){
                for(d in value.documents){
                    val bildiri = d.toObject(Bildiri::class.java)
                    if(bildiri != null){
                        bildiri.id = d.id
                        liste2.add(bildiri)
                    }
                }
                bildiriList.value = liste2
            }

        }
        liste2.clear()
        return bildiriList
    }
    fun bildiriTiklanmasi(clickedBildiri: Bildiri,it:View) {
        val gecis=MapsFragmentDirections.actionMapsFragmentToDetayFragment(null,clickedBildiri)
        Navigation.findNavController(it).navigate(gecis)
    }

    fun getBildiriFromMarker(marker: Marker): Bildiri? {
        val markerPosition = marker.position
        for (bildiri in liste) {
            val bildiriLatitude: Double = bildiri.enlem
            val bildiriLongitude: Double = bildiri.boylam
            val bildiriLocation = LatLng(bildiriLatitude, bildiriLongitude)
            if (bildiriLocation == markerPosition) {
                return bildiri
            }
        }
        return null
    }
    fun generateSmallIcon(context: Context,id:Int,height:Int,width:Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources,id)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    fun toIsPark(parkItem: ParkItem,list:List<IsPark>):IsPark?{
        val pos=parkItem.position
        val lat=pos.latitude
        val lng=pos.longitude
        for (i in list){
            if (i.lat.toDouble()==lat&&i.lng.toDouble()==lng){
                return i
            }
        }
        return null
    }


    fun konumKaydet(tiklandiMi:Boolean, latitude:Double, longitude:Double, it: View, context: Context){
        if (tiklandiMi){
            val konum= Konum(latitude,longitude)
            val gecis= MapsFragmentDirections.actionMapsFragmentToDetayFragment(konum,null)
            Navigation.findNavController(it).navigate(gecis)
        }else{
            Toast.makeText(context,"Konum seçmelisiniz", Toast.LENGTH_SHORT).show()
        }
    }

}