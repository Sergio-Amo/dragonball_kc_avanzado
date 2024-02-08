package com.kc.dragonball_kc_avanzado.ui.details

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.kc.dragonball_kc_avanzado.R
import com.kc.dragonball_kc_avanzado.databinding.FragmentDetailsBinding
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            getHero(DetailsFragmentArgs.fromBundle(it).heroName)
        }

        //setInitialState()
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.hero.observe(viewLifecycleOwner) {
            with(binding) {
                // Name & Description
                heroName.text = it.name
                heroDescription.text = it.description
                // Image
                Glide
                    .with(root)
                    .load(it.photo)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(heroImage)
                // Favorite status
                favorite.colorFilter = if (it.favorite)
                    ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(1f) })
                else
                    ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
            }
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