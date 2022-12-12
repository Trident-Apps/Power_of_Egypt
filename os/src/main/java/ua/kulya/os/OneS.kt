package ua.kulya.os

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private var mapString: String? = null
private var valueForOneS = ""

fun initOneS(context: Context): Flow<String> = callbackFlow {
    val advID = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
    OneSignal.initWithContext(context)
    OneSignal.setAppId("a6482af3-04be-46b5-a733-1f926c6fb183")
    OneSignal.setExternalUserId(advID)
    trySend(advID)
    awaitClose()
}

fun provideValueForOneS(
    stringValue: String,
    mapValue: MutableMap<String, Any>?,
): String {
    mapString = mapValue?.get("campaign").toString()
    if (stringValue == "null" && mapString == "null") {
        valueForOneS = "organic"
    }
    if (stringValue != "null") {
        valueForOneS = stringValue.replace("myapp://", "").substringBefore("/")
    }
    if (mapString != "null") {
        valueForOneS = mapString!!.substringBefore("_")
    }
    return valueForOneS
}

fun sendOneS(valueString: String) {
    OneSignal.sendTag("key2", valueString)
}
