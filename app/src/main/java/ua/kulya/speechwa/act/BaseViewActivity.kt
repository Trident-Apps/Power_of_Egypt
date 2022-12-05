package ua.kulya.speechwa.act

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewActivity<Vb : ViewBinding> : AppCompatActivity() {
    protected var _bind: Vb? = null
    protected val bindView get() = _bind!!
    lateinit var navIntent: Intent

    abstract fun createView(): Vb
    abstract fun activitiesNav(intent: Intent)

    override fun onDestroy() {
        super.onDestroy()
        _bind = null
    }
}