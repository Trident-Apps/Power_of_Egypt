package ua.kulya.speechwa.utils

import android.content.Context
import androidx.core.net.toUri
import com.appsflyer.AppsFlyerLib
import java.util.TimeZone

lateinit var linkSource: String
lateinit var appsFlId: String

fun listForUrl(appLinkString: String, map: MutableMap<String, Any>?, ctx: Context): List<String> {
    val sourceList = mutableListOf<String>()
    if (appLinkString == "null") {
        linkSource = map?.get("media_source").toString()
        appsFlId = AppsFlyerLib.getInstance().getAppsFlyerUID(ctx).toString()
    } else {
        linkSource = "deeplink"
        appsFlId = "null"
    }
    sourceList.apply {
        add(appLinkString)
        add(linkSource)
        add(appsFlId)
        add(getStringFromMap(map, "adset_id"))
        add(getStringFromMap(map, "campaign_id"))
        add(getStringFromMap(map, "campaign"))
        add(getStringFromMap(map, "adset"))
        add(getStringFromMap(map, "adgroup"))
        add(getStringFromMap(map, "orig_cost"))
        add(getStringFromMap(map, "af_site_id"))
    }
    return sourceList
}

fun getStringFromMap(mutMap: MutableMap<String, Any>?, key: String): String {
    return mutMap?.get(key).toString()
}

fun postLink(
    baseUrl: String,
    customList: List<String>,
    advId: String
): String {
    return baseUrl.toUri().buildUpon().apply {
        appendQueryParameter("uaWv5WKOzL", "zufXpVQgur")
        appendQueryParameter("HvBRvWZPfY", TimeZone.getDefault().id)
        appendQueryParameter("0yDVQ2dRCP", advId)
        appendQueryParameter("SqVEuvFy6l", customList[0])
        appendQueryParameter("txyItinHlZ", customList[1])
        appendQueryParameter("xN5lFvaUAM", customList[2])
        appendQueryParameter("2y0CuxGnBc", customList[3])
        appendQueryParameter("JxcGRtnJ6l", customList[4])
        appendQueryParameter("J0PlacsKAd", customList[5])
        appendQueryParameter("lSDneUAL1r", customList[6])
        appendQueryParameter("wSk1Bs9bCl", customList[7])
        appendQueryParameter("NJgSt8yx3O", customList[8])
        appendQueryParameter("zkJDnf0V4g", customList[9])
    }.toString()
}