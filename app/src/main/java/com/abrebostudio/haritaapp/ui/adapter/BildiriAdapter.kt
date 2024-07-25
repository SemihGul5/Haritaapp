package com.abrebostudio.haritaapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.databinding.FragmentMapsBinding
import com.abrebostudio.haritaapp.databinding.MapFragmentListLayoutBinding
import com.abrebostudio.haritaapp.ui.viewmodel.MapsViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class BildiriAdapter(var context: Context,var bildiriList:List<Bildiri>,var viewModel:MapsViewModel) : RecyclerView.Adapter<BildiriAdapter.CardHolder>(){

    inner class CardHolder(var binding: MapFragmentListLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
       val binding= MapFragmentListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardHolder(binding)
    }

    override fun getItemCount(): Int {
        return bildiriList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val binding=holder.binding
        val bildiri=bildiriList.get(position)
        Picasso.get().load(bildiri.image).into(binding.imageViewList)
        binding.textViewListTitle.text=bildiri.title
        binding.textViewListDesc.text=bildiri.desc

        binding.listCard.setOnClickListener {
            Snackbar.make(it,bildiri.title,Snackbar.LENGTH_SHORT).show()
        }
    }


}