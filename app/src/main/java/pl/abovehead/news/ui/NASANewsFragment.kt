package pl.abovehead.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.abovehead.databinding.FragmentNewsBinding
import pl.abovehead.news.viewModel.NASANewsViewModel
import pl.abovehead.news.RssFeedAdapter
import pl.abovehead.news.domain.RssItem

@AndroidEntryPoint
class NASANewsFragment : Fragment() {

    companion object {
        @BindingAdapter("listItems")
        @JvmStatic
        fun bindRecyclerView(recyclerView: RecyclerView, items: List<RssItem>?) {
            val adapter = recyclerView.adapter as? RssFeedAdapter
            adapter?.submitList(items)
        }
    }

    private lateinit var viewModel: NASANewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentNewsBinding =
            FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = RssFeedAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[NASANewsViewModel::class.java]

        viewModel.rssItemsLiveData.observe(viewLifecycleOwner) { rssItems ->
            adapter.submitList(rssItems)
        }

        return view
    }
}