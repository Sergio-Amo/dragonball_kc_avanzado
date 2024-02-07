package com.kc.dragonball_kc_avanzado.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kc.dragonball_kc_avanzado.databinding.FragmentHeroListBinding

class HeroListFragment : Fragment() {

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
        //getHeroes()
        //setObservers()
        //setListeners()
    }

    private fun configureRecyclerView() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = heroAdapter
    }

}