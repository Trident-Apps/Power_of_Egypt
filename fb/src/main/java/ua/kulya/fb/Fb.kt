package ua.kulya.fb

import android.content.Context
import android.util.Log
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun getFbLink(ctx: Context): Flow<String> = callbackFlow {
    AppLinkData.fetchDeferredAppLinkData(ctx) {
//        trySend(it?.targetUri.toString())
        trySend(mockdeep)
        Log.d("customTag", "fb module, dp - $it")
    }
    awaitClose()
}

val mockdeep = "myapp://test1/test2/test3/test4/test5"