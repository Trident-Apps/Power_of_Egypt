package ua.kulya.speechwa.frag

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.biometrics.BiometricManager.Strings
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ua.kulya.speechwa.AfterSplashActivity
import ua.kulya.speechwa.R
import ua.kulya.speechwa.act.MenuActivity
import ua.kulya.speechwa.databinding.WebFragBinding
import ua.kulya.speechwa.room.EgyptUserData
import ua.kulya.speechwa.vm.EgyptUserVM

class WebFrag : Fragment(R.layout.web_frag) {
    private var _bind: WebFragBinding? = null
    private val bind get() = _bind!!
    lateinit var webView: WebView
    lateinit var chromeWebView: WebView
    private var isPageRedirected = false
    private var mValueCallback: ValueCallback<Array<Uri?>>? = null
    lateinit var webViewTransport: WebViewTransport
    lateinit var passedLink: String
    private lateinit var mVm: EgyptUserVM

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = WebFragBinding.inflate(inflater, container, false)
        passedLink = arguments?.getString("w", "haven't been passed").toString()
        mVm = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
            .create(EgyptUserVM::class.java)
        val activityResult =
            registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
                mValueCallback?.onReceiveValue(uris.toTypedArray())
            }
        webView = bind.web
        with(webView) {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    isPageRedirected = true
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (isPageRedirected) {
                        if (url == "https://goldenworld.ltd/") {
                            with(Intent(requireActivity(), MenuActivity::class.java)) {
                                requireActivity().startActivity(this)
                                requireActivity().finish()
                            }
                        } else {
                            mVm.allInfo.observe(viewLifecycleOwner) {
                                if (it.isNullOrEmpty()) {
                                    mVm.addUserDAta(EgyptUserData(1, url))
                                }
                            }
                        }
                    }
                }
            }
            loadUrl(passedLink)
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            settings.userAgentString = settings.userAgentString.replace("wv", "")
            settings.loadWithOverviewMode = false
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }

                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri?>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    mValueCallback = filePathCallback
                    activityResult.launch("image/*")
                    return true
                }

                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    chromeWebView = webView
                    with(chromeWebView.settings) {
                        javaScriptEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true
                        domStorageEnabled = true
                        setSupportMultipleWindows(true)
                        webViewTransport = resultMsg?.obj as WebView.WebViewTransport
                        webViewTransport.webView = chromeWebView
                        chromeWebView.webViewClient = object : WebViewClient() {
                            @Deprecated("Deprecated in Java")
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String?
                            ): Boolean {
                                view?.loadUrl(url ?: "")
                                isPageRedirected = false
                                return true
                            }
                        }
                    }
                    return true
                }
            }
        }
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    }
                }
            })
    }
}
