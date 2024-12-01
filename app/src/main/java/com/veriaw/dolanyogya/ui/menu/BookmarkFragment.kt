package com.veriaw.dolanyogya.ui.menu

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veriaw.dolanyogya.R
import com.veriaw.dolanyogya.adapter.BookmarkAdapter
import com.veriaw.dolanyogya.adapter.PlaceAdapter
import com.veriaw.dolanyogya.databinding.FragmentBookmarkBinding
import com.veriaw.dolanyogya.databinding.FragmentHomeBinding
import com.veriaw.dolanyogya.model.BookmarkViewModel
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.kriptografiapp.model.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var viewModel: BookmarkViewModel
    private val binding get() = _binding!!
    private lateinit var adapterPlaces: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(layoutInflater,container,false)
        viewModel=obtainViewModel(requireActivity())
        val rvPlaces = binding.rvBookmark
        adapterPlaces = BookmarkAdapter()
        rvPlaces.apply {
            adapter = adapterPlaces
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        // Reload bookmarks whenever the fragment is resumed
        loadBookmarks()
    }

    private fun loadBookmarks() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username",null)
        val userId = sharedPreferences.getInt("userId",0)
        viewModel.getCurrentBookmark(userId).observe(viewLifecycleOwner) { places ->
            adapterPlaces.submitList(places)
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): BookmarkViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(BookmarkViewModel::class.java)
    }
}