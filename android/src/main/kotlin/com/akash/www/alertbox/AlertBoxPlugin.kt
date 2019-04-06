package com.akash.www.alertbox

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class AlertBoxPlugin(val registrar: Registrar) : MethodCallHandler {
    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar): Unit {
            val channel = MethodChannel(registrar.messenger(), "alert_box")
            channel.setMethodCallHandler(AlertBoxPlugin(registrar))
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result): Unit {
        if (call.method.equals("toster")) {
            var message = call.argument<String>("message")
            var type = call.argument<String>("type")
            show(registrar.context(), message, type)
        } else if (call.method.equals("logger")) {
            var type = call.argument<String>("type")
            var key = call.argument<String>("key")
            var message = call.argument<String>("message")
            showLog(type, key, message)
        } else if (call.method.equals("net")) {
            isInternetConnected(registrar.context())
        } else {
            result.notImplemented()
        }
    }

    private fun showLog(type: String?, key: String?, message: String?) {
        when (type) {
            "0" -> Log.e(key, message)
            "1" -> Log.d(key, message)
            "2" -> Log.v(key, message)
            else -> Log.d(key, message)
        }
    }

    private fun show(context: Context, message: String, type: String) {
        if (!type.equals("0")) {
            Toast.makeText(context, message, Toast.LENGTH_LONG)
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT)
        }
    }

    fun isInternetConnected(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info.indices)
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }
}
