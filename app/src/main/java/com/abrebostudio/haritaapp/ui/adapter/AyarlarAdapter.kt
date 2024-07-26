package com.abrebostudio.haritaapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.databinding.FragmentAyarlarListBinding
import com.abrebostudio.haritaapp.ui.viewmodel.AyarlarViewModel
import com.google.android.material.snackbar.Snackbar

class AyarlarAdapter(var context: Context, var list: List<String>,var viewModel:AyarlarViewModel):RecyclerView.Adapter<AyarlarAdapter.ProfilListHolder>() {

    inner class ProfilListHolder(var binding:FragmentAyarlarListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilListHolder {
        val binding= FragmentAyarlarListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfilListHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProfilListHolder, position: Int) {
        val binding=holder.binding
        val ayar:String=list.get(position)
        binding.textViewProfilList.text=ayar

        viewModel.iconSetup(ayar,binding)

        binding.ayarlarCard.setOnClickListener {
            viewModel.ayarlarCliked(ayar,binding.root,context)
        }




    }


}