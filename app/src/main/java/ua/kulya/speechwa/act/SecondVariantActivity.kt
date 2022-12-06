package ua.kulya.speechwa.act

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.kulya.speechwa.R
import ua.kulya.speechwa.databinding.SecondVariantActivityBinding

class SecondVariantActivity : BaseViewActivity<SecondVariantActivityBinding>() {
    private var gameScore = 0
    private var repeat = 20
    lateinit var resText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = createView()
        setContentView(bindView.root)
        navIntent = Intent(this, MenuActivity::class.java)
        val gameList = listOf(
            bindView.gIv1,
            bindView.gIv2,
            bindView.gIv3,
            bindView.gIv4,
            bindView.gIv5,
            bindView.gIv6,
            bindView.gIv7,
            bindView.gIv8,
            bindView.gIv9
        )
        gameList.forEach {
            it.setOnClickListener { view ->
                view as ImageView
                click(view)
            }
        }
        bindView.g2Btn.setOnClickListener {
            activitiesNav(navIntent)
        }
        lifecycleScope.launch {
            while (repeat > 1 && gameScore != 5) {
                repeat--
                bindView.timerTv.text = "Time has left :$repeat sec"
                Log.d("gameTag", repeat.toString())
                delay(1000L)
                startGame()
            }
            if (gameScore < 5) {
                resText = getString(R.string.catch_eagle_lose)
            } else {
                resText = getString(R.string.catch_eagle_won)
            }
            enableAfterGameView(gameList, resText)
        }
    }

    private fun bindImageView(iv: ImageView, imageId: Int) {
        iv.setImageResource(imageId)
        iv.tag = imageId
    }

    private fun enableAfterGameView(list: List<ImageView>, afterGameText: String) {
        list.forEach {
            hideAnimation(it, afterGameText)
        }
    }

    private fun hideAnimation(iv: ImageView, string: String) {
        iv.animate().apply {
            duration = 1000L
            rotationXBy(1080F)
        }.withEndAction {
            iv.visibility = View.INVISIBLE
            with(bindView) {
                this.timerTv.visibility = View.INVISIBLE
                this.gameInstruction.visibility = View.INVISIBLE
                this.secondGameTitle.visibility = View.INVISIBLE
                this.endGameText.visibility = View.VISIBLE
                this.endGameText.text = string
                this.g2Btn.visibility = View.VISIBLE
            }
        }
    }

    private fun startGame() {
        val icList = mutableListOf(
            R.drawable.cloack_ic1,
            R.drawable.cloack_ic1,
            R.drawable.cloack_ic2,
            R.drawable.cloack_ic2,
            R.drawable.cloack_ic3,
            R.drawable.cloack_ic3,
            R.drawable.cloack_ic4,
            R.drawable.cloack_ic4,
            R.drawable.cloack_ic5
        ).shuffled()
        bindImageView(bindView.gIv1, icList[0])
        bindImageView(bindView.gIv2, icList[1])
        bindImageView(bindView.gIv3, icList[2])
        bindImageView(bindView.gIv4, icList[3])
        bindImageView(bindView.gIv5, icList[4])
        bindImageView(bindView.gIv6, icList[5])
        bindImageView(bindView.gIv7, icList[6])
        bindImageView(bindView.gIv8, icList[7])
        bindImageView(bindView.gIv9, icList[8])
    }

    private fun click(iv: ImageView) {
        if (iv.tag == R.drawable.cloack_ic5) {
            gameScore++
            bindView.gameInstruction.text = "Your score - $gameScore/5"
        }
    }

    override fun createView(): SecondVariantActivityBinding {
        return SecondVariantActivityBinding.inflate(layoutInflater)
    }

    override fun activitiesNav(intent: Intent) {
        startActivity(navIntent)
        this.finish()
    }
}