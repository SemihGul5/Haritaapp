package com.abrebostudio.haritaapp.retrofit

class ApiUtils {
    companion object{
        private const val BASE_URL="https://mekansal.herokuapp.com/api/"
        private const val BASE_URL_BIKE="https://api.ibb.gov.tr/ispark-bike/"
        private const val BASE_URL_DUYURU="http://10.0.2.2:5050/"
        private const val BASE_URL_ISPARK="https://api.ibb.gov.tr/ispark/"


        fun getMekansalDao(): MekansalDao{
            return RetrofitClient.getClient(BASE_URL).create(MekansalDao::class.java)
        }

        fun getBikeDao(): MekansalDao{
            return RetrofitClient.getClient(BASE_URL_BIKE).create(MekansalDao::class.java)
        }

        fun getDuyuruDao(): MekansalDao{
            return RetrofitClient.getClient(BASE_URL_DUYURU).create(MekansalDao::class.java)
        }
        fun getAraDuyuruDao(): MekansalDao{
            return RetrofitClient.getClient(BASE_URL_DUYURU).create(MekansalDao::class.java)
        }
        fun getParkDao(): MekansalDao{
            return RetrofitClient.getClient(BASE_URL_ISPARK).create(MekansalDao::class.java)
        }


    }
}