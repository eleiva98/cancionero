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


class CustomPagerAdapter(private val context: Context, private var htmlFilePaths: List<String>) : PagerAdapter() {

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
                }
        }
        webViewContainer.addView(webView)
        webView.loadUrl(htmlFiles[position])
        webViewMap[position] = webView

        container.addView(view)
        return view
    }

    fun updateFiles(newFiles: List<String>) {
        htmlFilePaths = newFiles
        notifyDataSetChanged()
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


