package br.com.usinasantafe.pcp.external.webservices

import android.content.Context
import android.content.res.Resources
import br.com.usinasantafe.pcp.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object AppRetrofit {

    private fun httpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .build()
    }

    private fun gson(): Gson = GsonBuilder().create()

    fun getInstance(appContext: Context): Retrofit = Retrofit.Builder()
            .baseUrl(appContext.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .client(httpClient())
            .build()

}