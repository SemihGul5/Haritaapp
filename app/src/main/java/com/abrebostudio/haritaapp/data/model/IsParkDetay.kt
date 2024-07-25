package com.abrebostudio.haritaapp.data.model

data class IsParkDetay(val locationName:String,
                       val parkID:Int,
                       val parkName:String,
                       val lat:String,
                       val lng:String,
                       val capacity:Int,
                       val emptyCapacity:Int,
                       val updateDate:String,
                       val workHours:String,
                       val parkType:String,
                       val freeTime:Int,
                       val monthlyFee:Int,
                       val tariff:String,
                       val district:String,
                       val address:String,
                       val areaPolygon:String) {
}