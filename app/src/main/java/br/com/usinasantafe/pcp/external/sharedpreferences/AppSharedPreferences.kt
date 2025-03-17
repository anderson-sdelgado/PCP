package br.com.usinasantafe.pcp.external.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import br.com.usinasantafe.pcp.utils.BASE_SHARE_PREFERENCES

fun providerSharedPreferences(appContext: Context): SharedPreferences {
    return appContext.getSharedPreferences(BASE_SHARE_PREFERENCES, Context.MODE_PRIVATE)
}