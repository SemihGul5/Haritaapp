package com.abrebostudio.haritaapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.databinding.FragmentBildirilerimBinding



class BildirilerimFragment : Fragment() {
    private lateinit var binding:FragmentBildirilerimBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentBildirilerimBinding.inflate(inflater, container, false)


        return binding.root
    }

}