package ru.axcheb.spotifyapi.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.databinding.CategoryItemBinding
import ru.axcheb.spotifyapi.ui.StrIdAwareDiffCallback
import ru.axcheb.spotifyapi.ui.circularPlaceholder

@Suppress("UNCHECKED_CAST")
class CategoriesAdapter :
    PagingDataAdapter<Category, CategoryViewHolder>(StrIdAwareDiffCallback as DiffUtil.ItemCallback<Category>) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(CategoryItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CategoryViewHolder(private val binding: CategoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category?) {
        binding.icon.load(category?.iconUrl) {
            placeholder(binding.icon.circularPlaceholder())
        }
        binding.name.text = category?.name
        category?.let {
            binding.root.setOnClickListener {
                val direction =
                    MainFragmentDirections.actionMainFragmentToPlaylistsFragment(category.id)
                it.findNavController().navigate(direction)
            }
        }
    }

}
