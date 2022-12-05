package ua.kulya.speechwa.utils

import android.content.Context
import androidx.core.net.toUri
import com.appsflyer.AppsFlyerLib
import java.util.TimeZone

lateinit var linkSource: String
lateinit var appsFlId: String
fun createLink(
    fbLink: String,
    afData: MutableMap<String, Any>?,
    ctx: Context,
    advId: String
): String {
    if (fbLink == "null") {
        linkSource = afData?.get("media_source").toString()
        appsFlId = AppsFlyerLib.getInstance().getAppsFlyerUID(ctx).toString()
    } else {
        linkSource = "deeplink"
        appsFlId = "null"
    }
    val createdLink = "https://goldenworld.ltd/powerofegypt.php".toUri().buildUpon().apply {
        appendQueryParameter("uaWv5WKOzL", "zufXpVQgur")
        appendQueryParameter("HvBRvWZPfY", TimeZone.getDefault().id)
        appendQueryParameter("0yDVQ2dRCP", advId)
        appendQueryParameter("SqVEuvFy6l", fbLink)
        appendQueryParameter("txyItinHlZ", linkSource)
        appendQueryParameter("xN5lFvaUAM", appsFlId)
        appendQueryParameter("2y0CuxGnBc", afData?.get("adset_id").toString())
        appendQueryParameter("JxcGRtnJ6l", afData?.get("campaign_id").toString())
        appendQueryParameter("J0PlacsKAd", afData?.get("campaign").toString())
        appendQueryParameter("lSDneUAL1r", afData?.get("adset").toString())
        appendQueryParameter("wSk1Bs9bCl", afData?.get("adgroup").toString())
        appendQueryParameter("NJgSt8yx3O", afData?.get("orig_cost").toString())
        appendQueryParameter("zkJDnf0V4g", afData?.get("af_siteid").toString())
    }.toString()
    return createdLink
}