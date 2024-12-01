package com.veriaw.dolanyogya.ui.detail

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.veriaw.dolanyogya.databinding.FragmentOverviewBinding
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.kriptografiapp.model.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private lateinit var viewModel: PlaceViewModel
    private val binding get() = _binding!!
    private var placesId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placesId = it.getInt("ID_PLACE")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)
        viewModel=obtainViewModel(requireActivity())
        placesId?.let {
            viewModel.getCurrentPlaces(it).observe(viewLifecycleOwner){place->
                Log.d("fragment places", "$place")
                val price = "Price : Rp."+place.price
                binding.tvPrice.text=price
                binding.tvDescription.text=place.description
            }
        }
        return binding?.root
    }


    private fun obtainViewModel(activity: FragmentActivity): PlaceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PlaceViewModel::class.java)
    }
}