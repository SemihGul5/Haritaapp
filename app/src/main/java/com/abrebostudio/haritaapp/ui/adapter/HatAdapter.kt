package com.abrebostudio.haritaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abrebostudio.haritaapp.data.model.Hat
import com.abrebostudio.haritaapp.databinding.FragmentBusMapBinding
import com.abrebostudio.haritaapp.databinding.HatLayoutBinding
import com.abrebostudio.haritaapp.ui.fragment.BusMapFragmentDirections
import com.google.android.material.snackbar.Snackbar

class HatAdapter(var mContext:Context,var hatList:List<Hat>):RecyclerView.Adapter<HatAdapter.HatCardHolder>() {



    inner class HatCardHolder(var binding: HatLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatCardHolder {
        val binding= HatLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HatCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return hatList.size
    }

    override fun onBindViewHolder(holder: HatCardHolder, position: Int) {
        val hat=hatList.get(position)
        val binding=holder.binding
        binding.textViewCardHat.text=hat.SHATKODU

        binding.hatCard.setOnClickListener {
            val gecis=BusMapFragmentDirections.actionBusMapFragmentToBusMapLocationFragment(hat)
            Navigation.findNavController(binding.root).navigate(gecis)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newHatList: List<Hat>) {
        hatList = newHatList
        notifyDataSetChanged()
    }
}