package ua.kulya.speechwa.act

import android.content.Intent
import android.os.Bundle
import ua.kulya.speechwa.databinding.StartMenuAcBinding

class MenuActivity : BaseViewActivity<StartMenuAcBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = createView()
        setContentView(bindView.root)

        with(bindView) {
            menuBtn.setOnClickListener {
                navIntent = Intent(this@MenuActivity, FirstVariantActivity::class.java)
                activitiesNav(navIntent)
            }
            menuBtn2.setOnClickListener {
                navIntent = Intent(this@MenuActivity, SecondVariantActivity::class.java)
                activitiesNav(navIntent)
            }
        }
    }

    override fun activitiesNav(intent: Intent) {
        startActivity(intent)
        this.finish()
    }

    override fun createView(): StartMenuAcBinding {
        return StartMenuAcBinding.inflate(layoutInflater)
    }
}