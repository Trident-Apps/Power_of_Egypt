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
import ua.kulya.os.sendOneS
import ua.kulya.speechwa.utils.createLink
import ua.kulya.speechwa.vm.EgyptUserVM

@SuppressLint("CustomSplashScreen")
class AfterSplashActivity : AppCompatActivity() {
    private lateinit var adbString: String
    private lateinit var mVm: EgyptUserVM
    private var linkForWeb: String? = null
    private var _someLink: String? = null
    private val link get() = _someLink
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.after_spalsh_ac)
        adbString = intent.getStringExtra("adb") ?: ""
        Log.d("customTag", "received adbValue from splash $adbString")
        mVm = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(EgyptUserVM::class.java)
        Log.d("customTag", "vm init")
        mVm.allInfo.observe(this) {
            if (!it.isNullOrEmpty()) {
                linkForWeb = it[0].link
                passToStart(linkForWeb!!)
                Log.d("customTag", "saved link is $linkForWeb")
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val context = this@AfterSplashActivity
                    initOneS(context).collect { advID ->
                        Log.d("customTag", " adv id is $advID")
                        getFbLink(context).collect { dp ->
                            Log.d("customTag", "when FB dp - $dp")
                            when (dp) {
                                "null" -> {
                                    getAfData(context).collect { af ->
                                        sendOneS("null", af)
                                        linkForWeb = createLink("null", af, context, advID)
                                        Log.d("customTag", "link from af $linkForWeb")

                                        lifecycleScope.launch(Dispatchers.Main) {
                                            passToStart(linkForWeb!!)
                                            Log.d("customTag", "passed from af")
                                        }
                                    }
                                }
                                else -> {
                                    sendOneS(dp, null)
                                    linkForWeb = createLink(dp, null, context, advID)
                                    Log.d("customTag", "link from dp - $linkForWeb")

                                    lifecycleScope.launch(Dispatchers.Main) {
                                        passToStart(linkForWeb!!)
                                        Log.d("customTag", "passed from dp")
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
//        val navCon = navController.navInflater.inflate(R.navigation.web_nav_praph)
//        navCon.setStartDestination(R.id.web)
//        val bundle = bundleOf("w" to webLink)
//        navController.setGraph(R.navigation.web_nav_praph, bundle)
    }
}