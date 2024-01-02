package pl.abovehead.news

// RssFeedAdapter.kt

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pl.abovehead.NewsDetailsActivity
import pl.abovehead.databinding.ListItemRssBinding
import pl.abovehead.news.domain.RssItem


class RssFeedAdapter :
    ListAdapter<RssItem, RssFeedAdapter.RssItemViewHolder>(RssItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRssBinding.inflate(inflater, parent, false)
        return RssItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RssItemViewHolder, position: Int) {
        val rssItem = getItem(position)
        holder.bind(rssItem)
    }

    class RssItemViewHolder(private val binding: ListItemRssBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RssItem) {
            binding.titleTextView.text = item.title
            binding.pubDate.text = item.pubDate
            binding.rssItemImage.load(Uri.parse(item.enclosureUrl))
            binding.root.setOnClickListener {
                val context = binding.root.context
                startActivity(
                    context,
                    Intent(context, NewsDetailsActivity::class.java).apply { putExtra("item", item) },
                    null
                )
            }
            // You can handle the click event or additional UI bindings here
        }
    }
}

class RssItemDiffCallback : DiffUtil.ItemCallback<RssItem>() {
    override fun areItemsTheSame(oldItem: RssItem, newItem: RssItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: RssItem, newItem: RssItem): Boolean {
        return oldItem == newItem
    }
}
