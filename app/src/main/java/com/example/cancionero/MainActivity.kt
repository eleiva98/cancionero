package com.example.cancionero

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.material.navigation.NavigationView
import com.example.cancionero.htmls.HtmlLoader
import com.example.cancionero.htmls.HtmlLoader.getLocalHtmlFiles
import com.example.cancionero.htmls.HtmlUpdater
import com.example.cancionero.menudrawer.DrawerInitializer
import com.example.cancionero.menudrawer.MenuNavigationHandler
import com.example.cancionero.pager.CustomPagerAdapter
import com.example.cancionero.pager.ViewPagerManager
import com.example.cancionero.pager.ViewPagerTitleUpdater
import com.example.cancionero.settings.SettingsActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CustomPagerAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var menuNavigationHandler: MenuNavigationHandler
    private lateinit var viewPagerManager: ViewPagerManager // Declarar viewPagerManager
    private lateinit var btnScrollToTop: Button
    //private lateinit var menuHandler: MenuHandler
   // private lateinit var btnMore: Button

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

        // Inicialización del ViewPager
        viewPager = findViewById(R.id.viewPager)
        btnScrollToTop = findViewById(R.id.btnScrollToTop)

        // 1. Copiar los HTML embebidos si es necesario
        HtmlLoader.copyDefaultHtmlFilesIfNeeded(this)
        val htmlFiles = getLocalHtmlFiles(this)
        Log.d("MainActivity", "Archivos HTML encontrados: $htmlFiles")

        //Buscar actualizaciones de canciones
        if (htmlFiles.isNotEmpty()) {
            pagerAdapter = CustomPagerAdapter(this, htmlFiles)
            viewPager.adapter = pagerAdapter
        }

        // Inicialización de ViewPagerManager
        viewPagerManager = ViewPagerManager(viewPager)

        val titles = listOf(
            R.string.section1, R.string.section2, R.string.section3,
            R.string.section4, R.string.section5, R.string.section6,
            R.string.section7, R.string.section8, R.string.section9,
            R.string.section10, R.string.section11, R.string.section12,
            R.string.section13, R.string.section14, R.string.section15,
            R.string.section16, R.string.section17, R.string.section18
        )

        ViewPagerTitleUpdater(this, titles).attachToViewPager(viewPager)

        btnScrollToTop.setOnClickListener {
            val currentWebView = pagerAdapter.getCurrentWebView(viewPager.currentItem)
            currentWebView?.scrollTo(0, 0)
        }


        // Inicialización del NavigationView y configuración del listener para la navegación
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        // Inicialización del MenuNavigationHandler para manejar la navegación
        menuNavigationHandler = MenuNavigationHandler(drawerLayout, viewPagerManager)



    }


    // Método llamado cuando se selecciona un elemento del NavigationView
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        menuNavigationHandler.handleNavigation(item)
        return true
    }
    override fun onBackPressed() {
        val currentWebView = pagerAdapter.getCurrentWebView(viewPager.currentItem)
        if (currentWebView?.canGoBack() == true) {
            currentWebView.goBack()
        } else {
            super.onBackPressed() // Aquí sí se cierra la app
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.more_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Log.d("TEST", "Botón de ajustes presionado")
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent) // Abre la pantalla de Ajustes
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun refreshPagerAdapter(newHtmlFiles: List<String>) {
        viewPager.adapter = null // Limpia el adaptador viejo
        pagerAdapter = CustomPagerAdapter(this, newHtmlFiles)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0
    }

    override fun onResume() {
        super.onResume()

        // Obtener la lista actualizada de archivos HTML
        val updatedFiles = HtmlLoader.getLocalHtmlFiles(this)

        // Recargar el ViewPager con las nuevas canciones
        refreshPagerAdapter(updatedFiles)
    }













}





