package com.example.cancionero

import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity

class ViewPagerTitleUpdater(private val activity: AppCompatActivity, private val titles: List<Int>) {
    fun attachToViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                activity.setTitle(titles.getOrNull(position) ?: return)
            }
        })
    }
}
