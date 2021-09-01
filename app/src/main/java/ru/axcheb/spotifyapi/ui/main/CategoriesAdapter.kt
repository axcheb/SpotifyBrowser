package ru.axcheb.spotifyapi.ui.main

import android.content.Context
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

class CategoriesAdapter(context: Context) :
    PagingDataAdapter<Category, CategoryViewHolder>(CategoryDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemCategoryBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

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