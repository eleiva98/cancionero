package com.example.cancionero

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.viewpager.widget.PagerAdapter



class CustomPagerAdapter (private val context: Context, private val htmlFiles: List<Int>) : PagerAdapter() {
    // Tamaño de letra actual seleccionado por el usuario
    private val createdWebViews = mutableListOf<WebView>()

    // Otros métodos de la clase

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val webView = createWebView()
        createdWebViews.add(webView)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_html_display, container, false)

        val webViewContainer: ViewGroup = view.findViewById(R.id.webView)
        webViewContainer.addView(webView)
        //updateWebViewTextSize(webView, textSize)

       webView.loadUrl("file:///android_res/raw/${htmlFiles[position]}")

        container.addView(view)
        return view
    }

    fun getSectionName(position: Int): String {
        val sectionNames = arrayOf(
            R.string.section1, R.string.section2, R.string.section3, // Agrega todos los nombres de las secciones aquí
        )
        return context.getString(sectionNames[position])
    }



    override fun getCount(): Int {
        return htmlFiles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun createWebView(): WebView {
        val webView = WebView(context)

        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.domStorageEnabled = true
        return webView
    }


}

