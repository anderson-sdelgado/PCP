package br.com.usinasantafe.pcp.external.retrofit

import android.content.Context
import br.com.usinasantafe.pcp.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


private fun provideHttpClient(): OkHttpClient {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(logging)
        .build()
}

fun provideRetrofit(appContext: Context): Retrofit = Retrofit.Builder()
        .baseUrl(appContext.getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .client(getUnsafeOkHttpClient())
        .build()

fun getUnsafeOkHttpClient(): OkHttpClient {

    try {

        val trustAllCertificates = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCertificates, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCertificates[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // Ignorar verificação de hostname
            .build()

    } catch (e: Exception) {
        throw RuntimeException("Erro ao configurar SSL inseguro", e)
    }

}
