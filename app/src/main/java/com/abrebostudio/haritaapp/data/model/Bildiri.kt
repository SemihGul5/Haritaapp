package com.abrebostudio.haritaapp.data.model

import com.google.firebase.firestore.GeoPoint
import java.io.Serializable

data class Bildiri(
    var id: String? = "",
    var image: String = "",
    var type: String = "",
    var title: String = "",
    var desc: String = "",
    var enlem: Double = 0.0,
    var boylam: Double = 0.0,
    var tarih: String = "",
    var email: String? = ""
):Serializable {

    constructor() : this("", "", "", "", "", 0.0, 0.0, "", "")
}
