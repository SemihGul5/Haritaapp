package com.abrebostudio.haritaapp.retrofit


import com.abrebostudio.haritaapp.data.model.BikeCollection
import com.abrebostudio.haritaapp.data.model.Duyuru
import com.abrebostudio.haritaapp.data.model.FeatureCollection
import com.abrebostudio.haritaapp.data.model.IsPark
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MekansalDao {
    @GET("filo")
    suspend fun busUpload(): FeatureCollection

    @GET("GetAllStationStatus")
    suspend fun bikeUpload(): BikeCollection

    @GET("api/announcements")
    suspend fun duyuruUpload(): List<Duyuru>

    @POST("api/search_announcements")
    @FormUrlEncoded
    suspend fun duyuruAraUpload(@Field("HATKODU") kelime:String): List<Duyuru>

    @GET("Park")
    suspend fun parkUpload():List<IsPark>
}