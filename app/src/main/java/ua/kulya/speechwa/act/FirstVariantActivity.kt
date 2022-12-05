package ua.kulya.speechwa.act

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import ua.kulya.speechwa.R
import ua.kulya.speechwa.databinding.FirstVariantActivityBinding

class FirstVariantActivity : BaseViewActivity<FirstVariantActivityBinding>() {

    private var fIv: ImageView? = null
    private var sIv: ImageView? = null
    private var _result = 0
    private var ivFlag1 = false
    private var ivFlag2 = false
    private val defaultDrawableList = listOf(
        R.drawable.cloack_ic2
    )

    private val listOfImages = listOf(
        R.drawable.cloack_ic1,
        R.drawable.cloack_ic3,
        R.drawable.cloack_ic4,
        R.drawable.cloack_ic5,
//        R.drawable.cloack_ic1,
//        R.drawable.cloack_ic3,
//        R.drawable.cloack_ic4,
//        R.drawable.cloack_ic5,
//        R.drawable.cloack_ic1,
//        R.drawable.cloack_ic3,
//        R.drawable.cloack_ic4,
//        R.drawable.cloack_ic5,
//        R.drawable.cloack_ic1,
//        R.drawable.cloack_ic3,
//        R.drawable.cloack_ic4,
//        R.drawable.cloack_ic5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = createView()
        setContentView(bindView.root)
        navIntent = Intent(this@FirstVariantActivity, MenuActivity::class.java)
        shuffleImageList(listOfImages)
        with(bindView) {
            returnBtn.setOnClickListener {
                activitiesNav(navIntent)
            }
            listOf(
                this.imV1,
                this.imV2,
                this.imV3,
                this.imV4,
                this.imV5,
                this.imV6,
                this.imV7,
                this.imV8,
                this.imV9,
                this.imV10,
                this.imV11,
                this.imV12,
                this.imV13,
                this.imV14,
                this.imV15,
                this.imV16
            ).forEach { imageVIew ->
                imageVIew.setOnClickListener { view ->
                    view as ImageView
                    if (fIv == null) {
                        fIv = view
                        ivFlag1 = true
                        setDrawable(fIv!!)
                    } else if (fIv != null && fIv != view) {
                        sIv = view
                        ivFlag2 = true
                        setDrawable(sIv!!)
                    }
                }
            }
        }
    }

    private fun checkViews(view1: ImageView, view2: ImageView) {
        if (view1.tag == view2.tag) {
            view1.isClickable = false
            view2.isClickable = false
            fIv = null
            sIv = null
            ivFlag1 = false
            ivFlag2 = false
            _result++
        } else {
            animateIv(view1, 0, defaultDrawableList)
            animateIv(view2, 0, defaultDrawableList)
            fIv = null
            sIv = null
            ivFlag1 = false
            ivFlag2 = false
        }
        if (_result == 8) {
            with(bindView) {
                listOf(
                    this.imV1,
                    this.imV2,
                    this.imV3,
                    this.imV4,
                    this.imV5,
                    this.imV6,
                    this.imV7,
                    this.imV8,
                    this.imV9,
                    this.imV10,
                    this.imV11,
                    this.imV12,
                    this.imV13,
                    this.imV14,
                    this.imV15,
                    this.imV16
                ).forEach {
                    it.animate().apply {
                        duration = 1000L
                        rotationYBy(1080F)
                    }.withEndAction {
                        it.visibility = View.INVISIBLE
                        returnBtn.visibility = View.VISIBLE
                        afterMatchText.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setDrawable(imageView: ImageView) {
        with(bindView) {
            when (imageView) {
                this.imV1 -> {
                    animateIv(imageView, 0, listOfImages)
                }
                this.imV2 -> {
                    animateIv(imageView, 1, listOfImages)
                }
                this.imV3 -> {
                    animateIv(imageView, 2, listOfImages)
                }
                this.imV4 -> {
                    animateIv(imageView, 3, listOfImages)
                }
                this.imV5 -> {
                    animateIv(imageView, 2, listOfImages)
                }
                this.imV6 -> {
                    animateIv(imageView, 1, listOfImages)
                }
                this.imV7 -> {
                    animateIv(imageView, 3, listOfImages)
                }
                this.imV8 -> {
                    animateIv(imageView, 0, listOfImages)
                }
                this.imV9 -> {
                    animateIv(imageView, 3, listOfImages)
                }
                this.imV10 -> {
                    animateIv(imageView, 2, listOfImages)
                }
                this.imV11 -> {
                    animateIv(imageView, 0, listOfImages)
                }
                this.imV12 -> {
                    animateIv(imageView, 1, listOfImages)
                }
                this.imV13 -> {
                    animateIv(imageView, 0, listOfImages)
                }
                this.imV14 -> {
                    animateIv(imageView, 3, listOfImages)
                }
                this.imV15 -> {
                    animateIv(imageView, 2, listOfImages)
                }
                this.imV16 -> {
                    animateIv(imageView, 1, listOfImages)
                }
            }
        }
    }

    private fun animateIv(imageView: ImageView, drawableId: Int, list: List<Int>) {
        imageView.animate().apply {
            duration = 1000L
            rotationYBy(1080F)
        }.withEndAction {
            imageView.setImageResource(list[drawableId])
            imageView.tag = list[drawableId]
            if (ivFlag1 && ivFlag2) {
                checkViews(fIv!!, sIv!!)
            }
        }
    }

    private fun shuffleImageList(imageList: List<Int>) {
        imageList.shuffled()
    }

    override fun createView(): FirstVariantActivityBinding {
        return FirstVariantActivityBinding.inflate(layoutInflater)
    }

    override fun activitiesNav(intent: Intent) {
        startActivity(intent)
        this@FirstVariantActivity.finish()
    }
}