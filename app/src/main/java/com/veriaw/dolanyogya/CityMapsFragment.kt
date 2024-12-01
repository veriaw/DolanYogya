package com.veriaw.dolanyogya

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.databinding.FragmentCityMapsBinding
import com.veriaw.dolanyogya.databinding.FragmentMapsBinding
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.kriptografiapp.model.ViewModelFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityMapsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityMapsFragment : Fragment() {
    private var _binding: FragmentCityMapsBinding? = null
    private lateinit var viewModel: PlaceViewModel
    private val binding get() = _binding!!
    private lateinit var map: MapView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityMapsBinding.inflate(layoutInflater, container, false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModel=obtainViewModel(requireActivity())
        setupMap()
        return binding?.root
    }

    @SuppressLint("MissingPermission")
    private fun setupMap(){
        if(isLocationPermissionGranted()){
            val lastLocation = fusedLocationProviderClient.lastLocation
            lastLocation.addOnSuccessListener { currentLoc->
                if(currentLoc != null){
                    map = binding.map
                    map.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                    map.setMultiTouchControls(true)
                    val originPoint = org.osmdroid.util.GeoPoint(currentLoc.latitude, currentLoc.longitude)
                    map.controller.setZoom(13.0)
                    map.controller.setCenter(originPoint)

                    val origin = Marker(map)
                    origin.position = originPoint
                    origin.title = "My Current Location"
                    origin.icon= ContextCompat.getDrawable(requireContext(), R.drawable.ic_origin)
                    map.overlays.add(origin)

                    viewModel.getAllPlaces().observe(viewLifecycleOwner){places->
                        places.forEach { place ->
                            addMarkerForPlace(place)
                        }
                    }
                    map.invalidate()

                }
            }

            lastLocation.addOnFailureListener {
                Log.d("Current Loc","Failed to get Current Location")
            }
        }
    }

    private fun addMarkerForPlace(place: PlaceEntity) {
        val placeMarker = Marker(map)
        placeMarker.position = place.latitude?.let { place.longitude?.let { it1 ->
            org.osmdroid.util.GeoPoint(it,
                it1
            )
        } }
        placeMarker.title = place.placeName // Assuming 'name' is a property of your Place model
        map.overlays.add(placeMarker)
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1001
            )
            false
        } else {
            true
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): PlaceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PlaceViewModel::class.java)
    }

}