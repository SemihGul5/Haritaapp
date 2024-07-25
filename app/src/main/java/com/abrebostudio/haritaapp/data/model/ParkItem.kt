package com.abrebostudio.haritaapp.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ParkItem(
    lat: String,
    lng: String,
    title: String,
    snippet: String
) : ClusterItem {

    private val position: LatLng = LatLng(lat.toDouble(), lng.toDouble())
    private val title: String
    private val snippet: String

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String {
        return snippet
    }


    init {
        this.title = title
        this.snippet = snippet
    }
}