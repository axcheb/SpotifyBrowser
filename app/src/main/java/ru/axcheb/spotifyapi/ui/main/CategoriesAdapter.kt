package ru.axcheb.spotifyapi.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.databinding.ItemCategoryBinding

class CategoriesAdapter :
    PagingDataAdapter<Category, CategoryViewHolder>(CategoryDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(ItemCategoryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category?) {
        binding.icon.load(category?.iconUrl) {
            placeholder(ColorDrawable(Color.TRANSPARENT))
        }
        binding.name.text = category?.name
    }

}

private object CategoryDiffItemCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}