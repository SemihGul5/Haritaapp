package com.abrebostudio.haritaapp.data.model

data class IsPark(val parkID:Int,
                  val parkName:String,
                  val lat:String,
                  val lng:String,
                  val capacity:Int,
                  val emptyCapacity:Int,
                  val workHours:String,
                  val parkType:String,
                  val freeTime:Int,
                  val district:String,
                  val isOpen:Int) {
}