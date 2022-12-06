package ua.kulya.fb

import android.content.Context
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun getFbLink(ctx: Context): Flow<String> = callbackFlow {
    AppLinkData.fetchDeferredAppLinkData(ctx) {
        trySend(it?.targetUri.toString())
    }
    awaitClose()
}