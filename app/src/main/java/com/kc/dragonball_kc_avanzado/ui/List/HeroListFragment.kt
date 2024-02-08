package com.kc.dragonball_kc_avanzado.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc.dragonball_kc_avanzado.databinding.FragmentHeroListBinding
import com.kc.dragonball_kc_avanzado.domain.model.HeroList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeroListFragment : Fragment() {

    private val viewModel: HeroListFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentHeroListBinding
    private val heroAdapter = HeroListRecyclerViewAdapter { hero, favorite ->
        if (favorite) {
            viewModel.toggleFavorite(hero)
            hero.favorite = !hero.favorite
            updateHero(hero)
        }
        else
            findNavController().navigate(HeroListFragmentDirections.actionHeroListFragmentToDetailsFragment(hero.name))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHeroListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        getHeroes()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        // Button to clear all stored content, both token and cached heroes will be removed
        binding.forget.setOnClickListener {
            viewModel.removeLocalToken()
            Toast.makeText(context, "All local data has been removed", Toast.LENGTH_LONG).show()
            activity?.finish()
        }
    }

    private fun configureRecyclerView() {
        with (binding) {
            forget.isVisible = viewModel.isPersistentSession()
            list.layoutManager = LinearLayoutManager(requireContext())
            list.adapter = heroAdapter
        }
    }

    private fun getHeroes() {
        viewModel.getHeroes()
    }

    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect { state ->
                when (state) {
                    is HeroListFragmentViewModel.State.Idle -> idle()
                    is HeroListFragmentViewModel.State.Error -> showError(state.errorMessage)
                    is HeroListFragmentViewModel.State.Loading -> showLoading(true)
                    is HeroListFragmentViewModel.State.HeroesLoaded -> populateList(state.heroes)
                    is HeroListFragmentViewModel.State.HeroUpdated -> updateHero(state.hero)
                }
            }
        }
    }

    private fun idle() {
        showLoading(false)
    }

    private fun showError(error: String) {
        showLoading(false)
        Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        binding.loadingSpinner.root.isVisible = show
    }

    private fun populateList(heroes: List<HeroList>) {
        heroAdapter.updateList(heroes)
        showLoading(false)
    }

    private fun updateHero(hero: HeroList) {
        heroAdapter.updateHero(hero)
    }
}