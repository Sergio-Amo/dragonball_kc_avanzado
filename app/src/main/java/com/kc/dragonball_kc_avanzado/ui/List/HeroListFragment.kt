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
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc.dragonball_kc_avanzado.databinding.FragmentHeroListBinding
import com.kc.dragonball_kc_avanzado.domain.model.HeroList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeroListFragment : Fragment() {

    private val viewModel: HeroListFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentHeroListBinding
    private val heroAdapter = HeroListRecyclerViewAdapter()

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
    }

    private fun configureRecyclerView() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = heroAdapter
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
                    is HeroListFragmentViewModel.State.HeroSelected -> showHeroDetails(state.hero)
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

    private fun showHeroDetails(hero: HeroList) {
        // TODO: Navigate
    }

}