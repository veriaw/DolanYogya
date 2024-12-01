package com.veriaw.dolanyogya.ui.detail

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.tabs.TabLayout
import com.veriaw.dolanyogya.R
import com.veriaw.dolanyogya.data.entity.BookmarkEntity
import com.veriaw.dolanyogya.databinding.ActivityDetailBinding
import com.veriaw.dolanyogya.model.BookmarkViewModel
import com.veriaw.dolanyogya.model.PlaceViewModel
import com.veriaw.dolanyogya.ui.MainActivity
import com.veriaw.kriptografiapp.model.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val placesid=intent.getIntExtra("ID_PLACE",0)
        placeViewModel=obtainPlaceViewModel(this)
        bookmarkViewModel=obtainBookmarkViewModel(this)
        val tabLayout = binding.tabLayout
        Log.d("id place","$placesid")
        placeViewModel.getCurrentPlaces(placesid).observe(this, Observer {place->
            binding.tvPlacename.text=place.placeName
            val rate = place.rating_avg?.div(10)
            binding.ratingText.text=rate.toString()
            binding.tvCategory.text=place.category
            Glide.with(this)
                .load(place.pictureUrl?.trim())
                .into(binding.tvImage)
            showFragment(OverviewFragment().apply {
                arguments = Bundle().apply {
                    putInt("ID_PLACE", placesid)
                }
            })
            val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val username = sharedPreferences.getString("username",null)
            val userId = sharedPreferences.getInt("userId",0)
            bookmarkViewModel.isBookmarked(userId, placesid).observe(this, Observer { bookmark->
                if(bookmark != null){
                    isBookmarked = true
                }else{
                    isBookmarked = false
                }
            })
            binding.btnBook.setOnClickListener {
                val bookmark = BookmarkEntity().apply {
                    this.userId = userId
                    this.placeId = placesid
                }
                if (isBookmarked) {
                    // Jika sudah di-bookmark, hapus bookmark
                    bookmarkViewModel.deleteBookmark(userId, placesid)
                    iconChange(true) // Perbarui ikon
                    Toast.makeText(this, "Bookmark removed", Toast.LENGTH_SHORT).show()
                } else {
                    // Jika belum di-bookmark, tambahkan bookmark
                    bookmarkViewModel.insertBookmark(bookmark)
                    iconChange(false) // Perbarui ikon
                    Toast.makeText(this, "Bookmarked", Toast.LENGTH_SHORT).show()
                }
            }
            binding.btnBack.setOnClickListener {
                val backIntent = Intent(this, MainActivity::class.java)
                startActivity(backIntent)
            }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> showFragment(OverviewFragment().apply {
                            arguments = Bundle().apply {
                                putInt("ID_PLACE", placesid)
                            }
                        })
                        1 -> showFragment(MapsFragment().apply {
                            arguments = Bundle().apply {
                                putInt("ID_PLACE", placesid)
                            }
                        })
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    // Tidak perlu melakukan apa-apa di sini
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    // Tidak perlu melakukan apa-apa di sini
                }
            })
        })
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    private fun obtainPlaceViewModel(activity: AppCompatActivity): PlaceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(PlaceViewModel::class.java)
    }

    private fun obtainBookmarkViewModel(activity: AppCompatActivity): BookmarkViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(BookmarkViewModel::class.java)
    }

    private fun iconChange(isBookmarked: Boolean){
        val icon = if(isBookmarked){
            resources.getDrawable(R.drawable.ic_bookmarked, null)
        }else{
            resources.getDrawable(R.drawable.ic_unbookmarked, null)
        }
        binding.btnBook.icon=icon
    }
}