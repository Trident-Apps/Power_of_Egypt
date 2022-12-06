package ua.kulya.os

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun initOneS(context: Context): Flow<String> = callbackFlow {
    val advID = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
    OneSignal.initWithContext(context)
    OneSignal.setAppId("a6482af3-04be-46b5-a733-1f926c6fb183")
    OneSignal.setExternalUserId(advID)
    trySend(advID)
    awaitClose()
}

fun sendOneS(dpValue: String, afValue: MutableMap<String, Any>?) {
    val campName = afValue?.get("campaign").toString()

    if (campName == "null" && dpValue == "null") {
        OneSignal.sendTag("key2", "organic")
    } else if (dpValue != "null") {
        OneSignal.sendTag("key2", dpValue.replace("myapp://", "").substringBefore("/"))
    } else if (campName != "null") {
        OneSignal.sendTag("key2", campName.substringBefore("_"))
    }
}
