package com.example.cancionero.menudrawer
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.cancionero.R

//Clase para la inicialización de la barra lateral
class DrawerInitializer (private val activity: AppCompatActivity, private val drawerLayout: DrawerLayout, private val toolbar: Toolbar)
{

    fun initialize() {
        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color = activity.resources.getColor(android.R.color.white)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


}