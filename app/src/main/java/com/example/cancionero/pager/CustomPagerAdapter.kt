package com.example.cancionero.pager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewpager.widget.PagerAdapter
import com.example.cancionero.settings.PreferenceUtils.loadChordVisibilityPreference
import com.example.cancionero.R


class CustomPagerAdapter(private val context: Context, private val htmlFilePaths: List<String>) : PagerAdapter() {
    // Tamaño de letra actual seleccionado por el usuario
    private val createdWebViews = mutableListOf<WebView>()
    
    // Lista de URLs alojadas en Firebase Hosting
    /*private val htmlUrls = listOf(
        "https://cancionero-2024.web.app/canciones1.htm",
        "https://cancionero-2024.web.app/canciones2.htm",
        "https://cancionero-2024.web.app/canciones3.htm",
        "https://cancionero-2024.web.app/canciones4.htm",
        "https://cancionero-2024.web.app/canciones5.htm",
        "https://cancionero-2024.web.app/canciones6.htm",
        "https://cancionero-2024.web.app/canciones7.htm",
        "https://cancionero-2024.web.app/canciones8.htm",
        "https://cancionero-2024.web.app/canciones9.htm",
        "https://cancionero-2024.web.app/canciones10.htm",
        "https://cancionero-2024.web.app/canciones11.htm",
        "https://cancionero-2024.web.app/canciones12.htm",
        "https://cancionero-2024.web.app/canciones13.htm",
        "https://cancionero-2024.web.app/canciones14.htm",
        "https://cancionero-2024.web.app/canciones15.htm",
        "https://cancionero-2024.web.app/canciones16.htm",
        "https://cancionero-2024.web.app/canciones17.htm",
        "https://cancionero-2024.web.app/canciones18.htm",) */

    private val htmlFiles: List<String> = htmlFilePaths

   private val webViewMap = mutableMapOf<Int, WebView>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val webView = createWebView()


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_html_display, container, false)

        val webViewContainer: ViewGroup = view.findViewById(R.id.webView)


        webView.webViewClient=object : WebViewClient(){
            override fun onPageFinished (view: WebView, url:String?){
                super.onPageFinished (view,url)
                hideChords(webView)
            }
        }

        webViewContainer.addView(webView)


        webView.loadUrl("file://${htmlFiles[position]}")

        val showChords = loadChordVisibilityPreference(context)
        val js = if (showChords) {
            "document.querySelectorAll('.Acordes').forEach(e => e.style.display = 'block');"
        } else {
          "document.querySelectorAll('.Acordes').forEach(e => e.style.display = 'none');"
        }
        webViewMap[position] = webView

        container.addView(view)
        return view
    }

    override fun getCount(): Int {return htmlFiles.size}

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun getCurrentWebView(position: Int): WebView?{
        return webViewMap[position]
    }

    private fun createWebView(): WebView {
        val webView = WebView(context)

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true
		webView.canGoBack()
		webView.goBack()

        return webView}


}
fun hideChords(webView: WebView) {
    val js = """
        javascript:(function() {
            var acordes = document.querySelectorAll('.Acordes');
            for (var i = 0; i < acordes.length; i++) {
                acordes[i].style.display = 'none';
            }
        })()
    """.trimIndent()

    webView.evaluateJavascript(js, null)
}


