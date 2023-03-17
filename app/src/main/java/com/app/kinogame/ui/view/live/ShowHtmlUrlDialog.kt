package com.app.kinogame.ui.view.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.app.kinogame.R
import com.app.kinogame.databinding.FragmentDialogUrlBinding
import com.app.kinogame.ui.util.getResourceColor
import com.app.kinogame.ui.util.setVisibleOrGone
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShowUrlDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogUrlBinding? = null
    private val binding get() = _binding!!
    var url: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDialogUrlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initData()
    }

    private fun setWebView(url: String?) {
        binding.webView.clearCache(true)
        binding.webView.clearHistory()
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.allowContentAccess = true
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.databaseEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.setNeedInitialFocus(true)

        binding.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        binding.webView.isScrollbarFadingEnabled = false
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.setInitialScale(90)
        binding.webView.setBackgroundColor(binding.webView.context.getResourceColor(R.color.webview_background))
        setWebViewClientForNewerVersions(binding.webView)
        url?.let {
            binding.webView.loadUrl(url)
        }
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptThirdPartyCookies(binding.webView, true)
    }

    private fun setWebViewClientForNewerVersions(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return false
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                binding.progress.setVisibleOrGone(false)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progress.setVisibleOrGone(false)
            }
        }
    }

    private fun initData() {
        setWebView(url)
    }

    private fun initListeners() {
//        binding.btnClose.setOnClickListener {
//            dismiss()
//        }
    }
}
