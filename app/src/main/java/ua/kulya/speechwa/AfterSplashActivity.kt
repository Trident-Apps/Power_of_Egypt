package ua.kulya.speechwa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.kulya.af.getAfData
import ua.kulya.fb.getFbLink
import ua.kulya.os.initOneS
import ua.kulya.os.provideValueForOneS
import ua.kulya.os.sendOneS
import ua.kulya.speechwa.utils.listForUrl
import ua.kulya.speechwa.utils.postLink
import ua.kulya.speechwa.vm.EgyptUserVM

@SuppressLint("CustomSplashScreen")
class AfterSplashActivity : AppCompatActivity() {
    private lateinit var mVm: EgyptUserVM
    private var isFbWorked = false
    private var linkForWeb: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.after_spalsh_ac)
        mVm = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(EgyptUserVM::class.java)
        mVm.allInfo.observe(this) {
            if (!it.isNullOrEmpty()) {
                linkForWeb = it[0].link
                passToStart(linkForWeb!!)
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val context = this@AfterSplashActivity
                    initOneS(context).collect { advID ->
                        Log.d("adasdasdas", "onCreate: after prefs check")
                        getFbLink(context).collect { dp ->
                            when (dp) {
                                "null" -> {
                                    getAfData(context).collect { af ->
                                        sendOneS(provideValueForOneS("null", af))
                                        linkForWeb = postLink(
                                            context.getString(R.string.base),
                                            listForUrl("null", af, context),
                                            advID
                                        )
                                        lifecycleScope.launch(Dispatchers.Main) {
                                            passToStart(linkForWeb!!)
                                        }
                                    }
                                }
                                else -> {
                                    sendOneS(provideValueForOneS(dp, null))
                                    linkForWeb = postLink(
                                        context.getString(R.string.base),
                                        listForUrl(dp, null, context),
                                        advID
                                    )
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        passToStart(linkForWeb!!)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun passToStart(webLink: String) {
        val bundle = bundleOf("w" to webLink)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHost.navController
        navController.setGraph(R.navigation.web_nav_praph, bundle)
    }
}