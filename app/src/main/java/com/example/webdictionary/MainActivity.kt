package com.example.webdictionary

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adBlockerJs: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupWebView()

        adBlockerJs = assets.open("adBlocker.js").bufferedReader().use { it.readText() }
        handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent()
    }

    override fun onSearchRequested(): Boolean {
        Log.e("congqin", ">>>>>>>>>>congqin onSearchRequested")

        return super.onSearchRequested()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_toolbarsearch).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity

//            searchView.setMaxWidth(Integer.MAX_VALUE);
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            maxWidth = Int.MAX_VALUE

            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        return true
    }


    private fun handleIntent() {


        var searchWord: CharSequence? = null

        Log.e("congqin", ">>>>>>>>>>congqin ${intent.action}")

        if (Intent.ACTION_PROCESS_TEXT == intent.action) {
            val text = intent
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
            if (text != null) {
                searchWord = text
            }

        }

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                searchWord = query
            }

            Log.e("congqin", ">>>>>>>>>>congqin ACTION_SEARCH")
        }

        searchWord = searchWord?.trim()

        if (!TextUtils.isEmpty(searchWord)) {
            val url = "https://dictionary.cambridge.org/dictionary/english-chinese-traditional/$searchWord"
            webView.loadUrl(url)
        }
    }


    private fun setupWebView() {

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                Log.e("congqin", ">>>>>>>>>>congqin onPageStarted")
                super.onPageStarted(view, url, favicon)


                view?.evaluateJavascript(adBlockerJs, null)
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                Log.e("congqin", ">>>>>>>>>>congqin onPageFinished")
                super.onPageFinished(view, url)
                val js = "window.addEventListener('load',() => {console.log('>>>>>>>>>>>congqin load');});"

            }
        }

        webView.webChromeClient = object : WebChromeClient() {


        }

    }

}
