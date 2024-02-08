package com.kc.dragonball_kc_avanzado.ui.list

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kc.dragonball_kc_avanzado.R
import com.kc.dragonball_kc_avanzado.databinding.HeroItemBinding
import com.kc.dragonball_kc_avanzado.domain.model.HeroList

/**
 * [RecyclerView.Adapter] that can display a [HeroList].
 */
class HeroListRecyclerViewAdapter(private val onClick: (HeroList, Boolean) -> Unit) :
    RecyclerView.Adapter<HeroListRecyclerViewAdapter.ViewHolder>() {

    private var heroes: List<HeroList> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HeroItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showHero(heroes[position])
        holder.addListeners(heroes[position])
    }

    override fun getItemCount(): Int = heroes.size

    inner class ViewHolder(binding: HeroItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val heroName: TextView = binding.heroName
        private val heroImage: ImageView = binding.heroImage
        private val heroFavorite: ImageButton = binding.favorite
        private val root: CardView = binding.root

        fun showHero(hero: HeroList) {
            // Name
            heroName.text = hero.name
            // Image
            Glide
                .with(root)
                .load(hero.photo)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(heroImage)
            // Favorite status
            heroFavorite.colorFilter = if (hero.favorite)
                ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(1f) })
            else
                ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
        }

        fun addListeners(hero: HeroList) {
            itemView.setOnClickListener {
                onClick(hero, false)
            }
            heroFavorite.setOnClickListener {
                onClick(hero, true)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + heroName.text + "'"
        }
    }

    fun updateList(heroes: List<HeroList>) {
        this.heroes = heroes
        notifyDataSetChanged()
    }
    fun updateHero(hero: HeroList) {
        notifyItemChanged(this.heroes.indexOf(hero))
    }

}