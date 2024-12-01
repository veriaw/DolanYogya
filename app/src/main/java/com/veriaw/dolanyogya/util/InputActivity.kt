package com.veriaw.dolanyogya.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.databinding.ActivityInputBinding
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.kriptografiapp.model.ViewModelFactory

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private lateinit var viewModel: PlaceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=obtainViewModel(this)
        val place = PlaceEntity()
        binding.btnSubmit.setOnClickListener {
            place.placeName=binding.tfPlaceName.text.toString()
            place.description=binding.tfDescription.text.toString()
            place.category=binding.tfCategory.text.toString()
            place.city=binding.tfCity.text.toString()
            place.price=binding.tfPrice.text.toString().trim().toInt()
            place.rating_avg=binding.tfRatingAvg.text.toString().trim().toInt()
            place.latitude=binding.tfLatitude.text.toString().trim().toDouble()
            place.longitude=binding.tfLongitude.text.toString().trim().toDouble()
            place.pictureUrl=binding.tfPictureUrl.text.toString()
            viewModel.insertPlace(place)
        }

        binding.btnClear.setOnClickListener {
            binding.tfPlaceName.setText("")
            binding.tfDescription.setText("")
            binding.tfCategory.setText("")
            binding.tfCity.setText("")
            binding.tfPrice.setText("")
            binding.tfRatingAvg.setText("")
            binding.tfLatitude.setText("")
            binding.tfLongitude.setText("")
            binding.tfPictureUrl.setText("")
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): PlaceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PlaceViewModel::class.java)
    }
}