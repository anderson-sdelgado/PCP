package br.com.usinasantafe.pcp.util.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnRetrofit {

    private static ConnRetrofit instance = null;

    public static ConnRetrofit getInstance() {
        if (instance == null) {
            instance = new ConnRetrofit();
        }
        return instance;
    }

    private OkHttpClient getHttpClient(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(httpLoggingInterceptor)
                .build();

    }

    private Gson getGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public Retrofit conn(){
        return new Retrofit.Builder()
                        .baseUrl(UrlsConexaoHttp.url)
                        .addConverterFactory(GsonConverterFactory.create(getGson()))
                        .client(getHttpClient())
                        .build();
    }

}
