package com.abrebostudio.haritaapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.abrebostudio.haritaapp.R
import com.abrebostudio.haritaapp.data.model.Bildiri
import com.abrebostudio.haritaapp.data.model.Konum
import com.abrebostudio.haritaapp.databinding.FragmentDetayBinding
import com.abrebostudio.haritaapp.ui.viewmodel.DetayViewModel
import com.squareup.picasso.Picasso
import java.io.IOException



class DetayFragment : Fragment() {
    private lateinit var binding:FragmentDetayBinding
    private lateinit var viewModel: DetayViewModel
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedBitmap : Bitmap? = null
    private var imageData: Uri?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_detay, container, false)
        val bundle=DetayFragmentArgs.fromBundle(requireArguments())
        binding.detayFragment=this
        binding.fragmentDetayToolbarTitle="Detay"
        binding.konumObject=bundle.konum


        if (bundle.konum==null){
            binding.buttonGonder.visibility=View.GONE
            val bildiri=bundle.bildiri!!
            val type=bildiri.type
            if (type=="Öneri"){
                binding.radioButtonOneri.isChecked=true
            }else{
                binding.radioButtonSikayet.isChecked=true
            }
            Picasso.get().load(bildiri.image).into(binding.imageViewFotograf)
            binding.textBaslik.setText(bildiri.title)
            binding.textAciklama.setText(bildiri.desc)
        }else{
            registerLauncher()
            binding.imageViewFotograf.setOnClickListener {
                viewModel.fotograf_izin(it,requireContext(),requireActivity(),permissionLauncher,activityResultLauncher)
            }

        }


        return binding.root
    }


    fun buttonGonder(konum: Konum,title:String,desc:String){
        binding.progressBarDetay.visibility=View.VISIBLE
        if (imageData!=null && title != "" && desc!=""&&!binding.radioGrup.isSelected){
            val selectedString=viewModel.getRadioButtonTextDetay(binding.radioGrup)
            val tarih=viewModel.getDate()
            val bildiri=Bildiri("","",selectedString,title,desc,konum.enlem,konum.boyam,tarih)
            viewModel.uploadImageAndSaveData(imageData!!,bildiri,requireContext())
            viewModel.textClearDetay(binding.imageViewFotograf,binding.radioGrup,binding.textBaslik,binding.textAciklama)
        }else{
            Toast.makeText(requireContext(),"Tüm alanlar dolu olmalıdır",Toast.LENGTH_SHORT).show()
        }
        binding.progressBarDetay.visibility=View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:DetayViewModel by viewModels()
        viewModel=temp
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    imageData = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(requireActivity().contentResolver, imageData!!)
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageViewFotograf.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageData)
                            binding.imageViewFotograf.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(RequestPermission()) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }
    }
}