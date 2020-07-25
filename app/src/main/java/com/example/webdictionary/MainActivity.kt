package com.example.webdictionary

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    private lateinit var adBlockerJs: String
    private var searchEnglish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logE("oncreate")
        setContentView(R.layout.activity_main)

        setupWebView()
        handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent()
    }

    override fun onSearchRequested(): Boolean {

        logD("onSearchRequested")

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

        logE("handleIntent ${intent.action}")

        if (Intent.ACTION_PROCESS_TEXT == intent.action) {
            if (intent.flags and Intent.FLAG_ACTIVITY_NEW_TASK == 0) {
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
                return
            }
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

            logE("handleIntent  ACTION_SEARCH")
        }

        searchWord = searchWord?.trim()

        if (!TextUtils.isEmpty(searchWord)) {
            val url: String

            if (Utils.isAlphaWord(searchWord!!)) { // search English words
                searchEnglish = true
                url =
                    "https://dictionary.cambridge.org/dictionary/english-chinese-traditional/$searchWord"
                adBlockerJs = assets.open("adBlocker_dictionary.cambridge.org.js").bufferedReader()
                    .use { it.readText() }
            } else { // search Japanese words
                searchEnglish = false
                url = "https://www.weblio.jp/content/${URLEncoder.encode(
                    searchWord.toString(),
                    "utf-8"
                )}?smtp=smp_apl_and"
                adBlockerJs =
                    assets.open("adBlocker_www.weblio.jp.js").bufferedReader().use { it.readText() }
            }

            webView.loadUrl(url)
        }
    }


    private fun setupWebView() {

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

                logE("WebViewClient onPageStarted")
                super.onPageStarted(view, url, favicon)


                view?.evaluateJavascript(adBlockerJs, null)
            }

            override fun onPageFinished(view: WebView?, url: String?) {

                logE("WebViewClient onPageFinished")
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                logE("WebViewClient onLoadResource $url")
            }


            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                logE("WebViewClient shouldOverrideUrlLoading $url")

                url?.let {
                    if (it.contains("www.weblio.jp") && !it.contains("smtp=smp_apl_and")) {
                        val newUrl =
                            if (it.contains("?")) "${it}&smtp=smp_apl_and" else "${it}?smtp=smp_apl_and"
                        view!!.loadUrl(newUrl)
                        return true
                    }
                }


                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                if (!searchEnglish) {
                    request?.url?.let {
                        if (it.toString().contains(".js")) {
                            return WebResourceResponse(
                                "text/javascript",
                                "UTF-8",
                                assets.open("empty.js")
                            )
                        }
                    }
                }
                return super.shouldInterceptRequest(view, request)
            }

        }

        webView.webChromeClient = object : WebChromeClient() {


        }

    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        super.onBackPressed()
    }

}
