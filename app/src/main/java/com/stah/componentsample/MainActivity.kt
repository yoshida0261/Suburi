package com.stah.componentsample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var mPager: ViewPager

    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPager = findViewById(R.id.pager)

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(mPager)
        viewModel.selectedItem.observe(this, Observer { item ->
            // Perform an action with the latest item data
            mPager.currentItem = 3
        })
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 5

        //override fun getItem(position: Int): Fragment = MainFragment()
        override fun getItem(i: Int): Fragment {
            val fragment = MainFragment()
            fragment.arguments = Bundle().apply {
                // Our object is just an integer :-P
                putInt(Companion.ARG_OBJECT, i + 1)
            }
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "OBJECT ${(position + 1)}"
        }
    }

    companion object {
        const val ARG_OBJECT = "object"
    }


}
