package com.example.cancionero

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

   private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CustomPagerAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var menuNavigationHandler: MenuNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Inicialización de la barra de herramientas (Toolbar)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inicialización del DrawerLayout y configuración del DrawerInitializer
        drawerLayout = findViewById(R.id.drawerLayout)
        val drawerInitializer = DrawerInitializer(this, drawerLayout, toolbar)

        drawerInitializer.initialize()

        viewPager = findViewById(R.id.viewPager)

        val titles = listOf(
            R.string.section1, R.string.section2, R.string.section3,
            R.string.section4, R.string.section5, R.string.section6,
            R.string.section7, R.string.section8, R.string.section9,
            R.string.section10, R.string.section11, R.string.section12,
            R.string.section13, R.string.section14, R.string.section15,
            R.string.section16
        )

        ViewPagerTitleUpdater(this, titles).attachToViewPager(viewPager)


        // Inicialización del ViewPagerManager y configuración de páginas HTML
        val viewPagerManager = ViewPagerManager(this, viewPager)
        viewPagerManager.initialize(getHtmlFiles())


        // Inicialización del NavigationView y configuración del listener para la navegación
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)


        pagerAdapter = CustomPagerAdapter(this, getHtmlFiles())
        viewPager.adapter = pagerAdapter

        // Inicialización del MenuNavigationHandler para manejar la navegación
        menuNavigationHandler = MenuNavigationHandler(drawerLayout, viewPagerManager)

        }


    // Método llamado cuando se selecciona un elemento del NavigationView
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        menuNavigationHandler.handleNavigation(item)
        return true
    }


    // Método para obtener la lista de archivos HTML
    private fun getHtmlFiles() = listOf(
        R.raw.canciones1,
        R.raw.canciones2,
        R.raw.canciones3,
        R.raw.canciones4,
        R.raw.canciones5,
        R.raw.canciones6,
        R.raw.canciones7,
        R.raw.canciones8,
        R.raw.canciones9,
        R.raw.canciones10,
        R.raw.canciones11,
        R.raw.canciones12,
        R.raw.canciones13,
        R.raw.canciones14,
        R.raw.canciones15,
        R.raw.canciones16,
        R.raw.canciones17,
    )





}





