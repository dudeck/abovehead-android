package pl.abovehead.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import pl.abovehead.R
import pl.abovehead.databinding.FragmentTabsNewsLayoutBinding

class NewsTabsFragment : Fragment() {
    private lateinit var demoCollectionAdapter: NewsAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentTabsNewsLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsNewsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = NewsAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = demoCollectionAdapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.blogNews)
                else -> getString(R.string.nasaNews)
            }
        }.attach()

    }
}

class NewsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlogNewsFragment()
            else -> NASANewsFragment()
        }
    }
}


