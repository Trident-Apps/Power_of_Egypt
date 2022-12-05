package ua.kulya.speechwa

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.kulya.speechwa.act.MenuActivity

class StartingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(1000L)
            afterSplash(checkerIfEnabled(this@StartingActivity))
        }
    }

    private fun checkerIfEnabled(activity: Activity): String {
        return Settings.Global.getString(activity.contentResolver, Settings.Global.ADB_ENABLED)
    }

    private fun afterSplash(adbString: String) {
        val packageContext = this
        val mIntent: Intent = if (adbString !g= "1") {
            Intent(packageContext, MenuActivity::class.java)
        } else {
            Intent(packageContext, AfterSplashActivity::class.java)
        }
        startActivity(mIntent)
        packageContext.finish()
    }
}