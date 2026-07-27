package br.com.usinasantafe.pcp.di.external.retrofit

import br.com.usinasantafe.pcp.external.retrofit.api.stable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRetrofitModule {

    @Provides
    @Singleton
    fun chaveApiRetrofit(
        retrofit: Retrofit
    ): ChaveApi = retrofit.create(ChaveApi::class.java)

    @Provides
    @Singleton
    fun colabApiRetrofit(
        retrofit: Retrofit
    ): ColabApi = retrofit.create(ColabApi::class.java)

    @Provides
    @Singleton
    fun equipApiRetrofit(
        retrofit: Retrofit
    ): EquipApi = retrofit.create(EquipApi::class.java)

    @Provides
    @Singleton
    fun fluxoApiRetrofit(
        retrofit: Retrofit
    ): FluxoApi = retrofit.create(FluxoApi::class.java)

    @Provides
    @Singleton
    fun localApiRetrofit(
        retrofit: Retrofit
    ): LocalApi = retrofit.create(LocalApi::class.java)

    @Provides
    @Singleton
    fun localTrabApiRetrofit(
        retrofit: Retrofit
    ): LocalTrabApi = retrofit.create(LocalTrabApi::class.java)

    @Provides
    @Singleton
    fun rLocalFluxoApiRetrofit(
        retrofit: Retrofit
    ): RLocalFluxoApi = retrofit.create(RLocalFluxoApi::class.java)

    @Provides
    @Singleton
    fun terceiroApiRetrofit(
        retrofit: Retrofit
    ): TerceiroApi = retrofit.create(TerceiroApi::class.java)

    @Provides
    @Singleton
    fun visitanteApiRetrofit(
        retrofit: Retrofit
    ): VisitanteApi = retrofit.create(VisitanteApi::class.java)

}
