package br.com.usinasantafe.cmm.common.utils

import android.content.Context
import android.net.ConnectivityManager

fun checkConn(context: Context): Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.isDefaultNetworkActive
}
