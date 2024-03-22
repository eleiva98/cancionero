package com.example.cancionero

import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MenuNavigationHandler(private val drawerLayout: DrawerLayout, private val viewPagerManager: ViewPagerManager) {

    fun handleNavigation(item: MenuItem) {
        val selectedIndex = when (item.itemId) {
            R.id.menu_canciones1 -> 0
            R.id.menu_canciones2 -> 1
            R.id.menu_canciones3 -> 2
            R.id.menu_canciones4 -> 3
            R.id.menu_canciones5 -> 4
            R.id.menu_canciones6 -> 5
            R.id.menu_canciones7 -> 6
            R.id.menu_canciones8 -> 7
            R.id.menu_canciones9 -> 8
            R.id.menu_canciones10 -> 9
            R.id.menu_canciones11 -> 10
            R.id.menu_canciones12 -> 11
            R.id.menu_canciones13 -> 12
            R.id.menu_canciones14 -> 13
            R.id.menu_canciones15 -> 14
            R.id.menu_canciones16 -> 15
            R.id.menu_canciones17 -> 16
            // Agrega los casos para los demás ítems del menú
            else -> -1
        }

        if (selectedIndex >= 0) {
            viewPagerManager.setCurrentItem(selectedIndex)
        }

        drawerLayout.closeDrawer(GravityCompat.START)
    }
}
