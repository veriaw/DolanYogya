package com.veriaw.dolanyogya.ui.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veriaw.dolanyogya.adapter.PlaceAdapter
import com.veriaw.dolanyogya.adapter.SearchAdapter
import com.veriaw.dolanyogya.databinding.FragmentHomeBinding
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.kriptografiapp.model.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: PlaceViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        viewModel=obtainViewModel(requireActivity())
        val rvPlaces = binding.rvPlaces
        val adapterPlaces = PlaceAdapter()
        rvPlaces.apply {
            adapter = adapterPlaces
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val rvSearch = binding.rvSearch
        val adapterSearch = SearchAdapter()
        rvSearch.apply {
            adapter = adapterSearch
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.searchEditText.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = p0.toString()
                if(searchText.isNullOrEmpty()){
                    adapterSearch.submitList(emptyList())
                }else{
                    Log.d("Search Keyword","$searchText")
                    viewModel.getSearchQuery("%$searchText%").observe(viewLifecycleOwner){places->
                        adapterSearch.submitList(places)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        viewModel.getAllPlaces().observe(viewLifecycleOwner){ places->
            adapterPlaces.submitList(places)
        }
        return binding?.root
    }

    private fun obtainViewModel(activity: FragmentActivity): PlaceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PlaceViewModel::class.java)
    }
}