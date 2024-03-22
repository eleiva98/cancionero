package com.example.cancionero
import android.content.Context
import androidx.viewpager.widget.ViewPager

class ViewPagerManager(private val context: Context, private val viewPager: ViewPager) {

    private lateinit var pagerAdapter: CustomPagerAdapter;

    fun initialize(htmlFiles: List<Int>) {
        pagerAdapter = CustomPagerAdapter(context, htmlFiles)
        viewPager.adapter = pagerAdapter
    }


    fun setCurrentItem(position: Int) {
        viewPager.currentItem = position
    }
}