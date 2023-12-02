package com.example.newsapi.ui.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.data.response.ArticlesItem
import com.example.newsapi.databinding.ListItemBinding
import com.example.newsapi.ui.list.ListFragmentDirections
import com.squareup.picasso.Picasso

class ListNewsAdapter : ListAdapter<ArticlesItem, ListNewsAdapter.ViewHolder>(DiffCallback()) {

    private var originalData: List<ArticlesItem?> = emptyList()
    private var filteredData: List<ArticlesItem?> = emptyList()

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: ArticlesItem) {
            binding.tvAuthor.text = newsItem.author
            binding.tvTitle.text = newsItem.title
            binding.tvContent.text = newsItem.content

            val baseUrl = "https://newsapi.org/v2/everything"
            val fullImageUrl = newsItem.urlToImage

            if (fullImageUrl != null) {
                if (fullImageUrl.isNotEmpty()) {
                    Picasso.get()
                        .load(fullImageUrl)
                        .error(R.drawable.ic_error)
                        .into(binding.imgThumbnail)
                } else {
                    binding.imgThumbnail.setImageResource(R.drawable.ic_error)
                }
            }

            binding.check.setOnClickListener {
                val actionToDetail =
                    ListFragmentDirections.actionListFragmentToDetailFragment(
                        newsTitle = newsItem.title,
                        newsAuthor = newsItem.author,
                        newsContent = newsItem.content,
                        newsImage = newsItem.urlToImage,
                        newsUrl = newsItem.url,
                        newsDate = newsItem.publishedAt,
                        newsDescription = newsItem.description
                    )
                val extras = FragmentNavigatorExtras(
                    binding.imgThumbnail to "thumbnail_transition"
                )
                Log.d("navigation", "sembarang")
                it.findNavController().navigate(actionToDetail, extras)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.bind(newsItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.source?.id == newItem.source?.id
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

    fun setData(data: List<ArticlesItem?>) {
        originalData = data
        filteredData = data
        submitList(data)
    }
}
