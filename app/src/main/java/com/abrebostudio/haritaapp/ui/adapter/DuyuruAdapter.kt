package com.abrebostudio.haritaapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrebostudio.haritaapp.data.model.Duyuru
import com.abrebostudio.haritaapp.databinding.DuyuruRvLayoutBinding
import com.abrebostudio.haritaapp.databinding.FragmentDuyuruBinding
import com.abrebostudio.haritaapp.ui.viewmodel.DuyurularViewModel

class DuyuruAdapter(var mcontex:Context,var list:List<Duyuru>,var viewModel:DuyurularViewModel): RecyclerView.Adapter<DuyuruAdapter.DuyuruCardHolder>() {

    inner class DuyuruCardHolder(var binding:DuyuruRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DuyuruCardHolder {
        val binding= DuyuruRvLayoutBinding.inflate(LayoutInflater.from(mcontex), parent, false)
        return DuyuruCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DuyuruCardHolder, position: Int) {
        val duyuru=list.get(position)
        val binding=holder.binding
        binding.duyuruHatKodu.text=duyuru.HATKODU
        binding.duyuruHat.text=duyuru.HAT
        binding.duyuruTip.text=duyuru.TIP
        binding.duyuruGuncellemeSaati.text=duyuru.GUNCELLEME_SAATI
        binding.duyuruMesaj.text=duyuru.MESAJ
    }
}