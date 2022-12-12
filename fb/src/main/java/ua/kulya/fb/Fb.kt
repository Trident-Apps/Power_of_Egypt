package ua.kulya.fb

import android.content.Context
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun getFbLink(ctx: Context): Flow<String> = callbackFlow {
    val pref = ctx.getSharedPreferences("fbPrefs", Context.MODE_PRIVATE)
    if (!pref.getBoolean("fb", false)) {
        AppLinkData.fetchDeferredAppLinkData(ctx) {
            trySend(it?.targetUri.toString())
            pref.edit()
                .putBoolean("fb", true)
                .apply()
        }
    }
    awaitClose()
}