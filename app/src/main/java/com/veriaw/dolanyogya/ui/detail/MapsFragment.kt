package com.veriaw.dolanyogya.ui.detail

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.veriaw.dolanyogya.R
import com.veriaw.dolanyogya.databinding.FragmentMapsBinding
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.kriptografiapp.model.ViewModelFactory
import org.osmdroid.config.Configuration
import org.osmdroid.util.BoundingBox
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


/**
 * A simple [Fragment] subclass.
 * Use the [MapsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
    private lateinit var viewModel: PlaceViewModel
    private val binding get() = _binding!!
    private var placesId: Int? = null
    private lateinit var map: MapView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placesId = it.getInt("ID_PLACE")
        }
        Configuration.getInstance()
            .load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
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
                    placesId?.let { viewModel.getCurrentPlaces(it).observe(viewLifecycleOwner){ place->
                        // Set lokasi awal peta
                        Log.d("geo point", "${place.latitude},${place.longitude}")

                        val destinationPoint = place.latitude?.let { it1 -> place.longitude?.let { it2 ->
                            org.osmdroid.util.GeoPoint(it1,
                                it2
                            )
                        } }
                        val boundingBox = place.latitude?.let { it1 ->
                            place.longitude?.let { it2 ->
                                makeBoundingBox(currentLoc.latitude,
                                    it1,currentLoc.longitude, it2
                                )
                            }
                        }
                        map.controller.setZoom(15.0)
                        map.zoomToBoundingBox(boundingBox, true);

                        // Menambahkan marker
                        val destination = Marker(map)
                        destination.position = destinationPoint
                        destination.title = place.placeName
                        map.overlays.add(destination)

                        val origin = Marker(map)
                        origin.position = originPoint
                        origin.title = "My Current Location"
                        origin.icon= ContextCompat.getDrawable(requireContext(), R.drawable.ic_origin)
                        map.overlays.add(origin)

                        map.invalidate()
                    } }
                }
            }

            lastLocation.addOnFailureListener {
                Log.d("Current Loc","Failed to get Current Location")
            }
        }
    }


    private fun makeBoundingBox(latitude1: Double, latitude2: Double, longitude1: Double, longitude2: Double): BoundingBox{
        val minLat: Double = Math.min(latitude1, latitude2)
        val maxLat: Double = Math.max(latitude1, latitude2)
        val minLon: Double = Math.min(longitude1, longitude2)
        val maxLon: Double = Math.max(longitude1, longitude2)

        val boundingBox = BoundingBox(maxLat+0.01, maxLon+0.01, minLat-0.01, minLon-0.01)
        return boundingBox
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