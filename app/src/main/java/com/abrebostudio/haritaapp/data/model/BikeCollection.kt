package com.abrebostudio.haritaapp.data.model

data class BikeCollection(val serviceCode:Int,
                          val serviceDesc:String,
                          val dataList:List<Datalist>) {
}