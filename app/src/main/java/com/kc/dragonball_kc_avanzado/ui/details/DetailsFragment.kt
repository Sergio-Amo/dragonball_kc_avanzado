package com.kc.dragonball_kc_avanzado.ui.details

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.kc.dragonball_kc_avanzado.R
import com.kc.dragonball_kc_avanzado.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by activityViewModels()

    private lateinit var gMap: GoogleMap
    private var heroReady = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        arguments?.let {
            getHero(DetailsFragmentArgs.fromBundle(it).heroName)
        }
        setObservers()
        setListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        // Listen for locations
        viewModel.locations.observe(viewLifecycleOwner) {
            it.sortedBy { location -> location.dateShow }.reversed()
            it.forEach { location ->
                gMap.addMarker(
                    MarkerOptions()
                        .position(location.latLng)
                        .title("${location.dateShow.toLocalDate()}, ${location.dateShow.toLocalTime()}")
                )
            }
            if (!it.isNullOrEmpty())
                gMap.moveCamera(CameraUpdateFactory.newLatLng(it.first().latLng))
        }

        // Fix map inside scrollView
        gMap.setOnCameraMoveStartedListener {
            binding.root.requestDisallowInterceptTouchEvent(true)
        }
        gMap.setOnCameraIdleListener {
            binding.root.requestDisallowInterceptTouchEvent(false)
        }

        getLocationIfReady()
    }

    private fun setObservers() {
        viewModel.hero.observe(viewLifecycleOwner) {

            with(binding) {
                // Name & Description
                heroName.text = it.name
                heroDescription.text = it.description
                // Image
                Glide.with(root).load(it.photo).centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground).into(heroImage)
                // Favorite status
                favorite.colorFilter =
                    if (it.favorite) ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(1f) })
                    else ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
            }
            heroReady = true
            getLocationIfReady()
        }
    }

    private fun getLocationIfReady() {
        if (this::gMap.isInitialized && heroReady) {
            Log.wtf("WTF", "locationCalled!")
            viewModel.getHeroLocation()
        }
    }

    private fun setListeners() {
        binding.favorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun getHero(hero: String) {
        viewModel.getHero(hero)
    }

}