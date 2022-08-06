package com.singularitycoder.memoryjog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.singularitycoder.memoryjog.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val topicTabsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewPager()
        binding.setUpUserActionListeners()
    }

    private fun setUpViewPager() {
        binding.viewpagerReminders.adapter = QuestionsViewPagerAdapter(fragmentManager = supportFragmentManager, lifecycle = lifecycle)
        TabLayoutMediator(binding.tabLayoutTopics, binding.viewpagerReminders) { tab, position ->
            tab.text = topicTabsList[position]
        }.attach()
    }

    private fun ActivityMainBinding.setUpUserActionListeners() {
        cardAddTopic.setOnClickListener {
            ibAddTopic.performClick()
        }
        ibAddTopic.setOnClickListener {
            topicTabsList.add("Science")
            tabLayoutTopics.addTab(
                tabLayoutTopics.newTab().apply {
                    text = topicTabsList[topicTabsList.lastIndex]
                },
                if (topicTabsList.isNotEmpty()) topicTabsList.lastIndex else 0,
                true
            )
            binding.viewpagerReminders.adapter?.notifyItemInserted(topicTabsList.lastIndex)
        }
    }

    inner class QuestionsViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = topicTabsList.size
        override fun createFragment(position: Int): Fragment {
            return QuestionsFragment.newInstance(topicTabsList[position])
        }
    }
}