package ua.kulya.af

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun getAfData(ctx: Context): Flow<MutableMap<String, Any>?> = callbackFlow {
    AppsFlyerLib.getInstance().init("", object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
            Log.d("customTag", "onConversionDataSuccess")
            trySend(p0)
        }

        override fun onConversionDataFail(p0: String?) {
            Log.d("customTag", "onConversionDataFail")
            trySend(null)
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

        override fun onAttributionFailure(p0: String?) {}

    }, ctx)
    AppsFlyerLib.getInstance().start(ctx)
    awaitClose()
}