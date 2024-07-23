package br.com.usinasantafe.pcp.module.persistence.retrofit

import android.content.Context
import br.com.usinasantafe.pcp.external.webservices.AppRetrofit
import br.com.usinasantafe.pcp.external.webservices.api.stable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRetrofitModule {

    @Provides
    @Singleton
    fun colabRetrofit(@ApplicationContext appContext: Context): ColabApi {
        return AppRetrofit.getInstance(appContext).create(ColabApi::class.java)
    }

    @Provides
    @Singleton
    fun equipRetrofit(@ApplicationContext appContext: Context): EquipApi {
        return AppRetrofit.getInstance(appContext).create(EquipApi::class.java)
    }

    @Provides
    @Singleton
    fun localRetrofit(@ApplicationContext appContext: Context): LocalApi {
        return AppRetrofit.getInstance(appContext).create(LocalApi::class.java)
    }

    @Provides
    @Singleton
    fun terceiroRetrofit(@ApplicationContext appContext: Context): TerceiroApi {
        return AppRetrofit.getInstance(appContext).create(TerceiroApi::class.java)
    }

    @Provides
    @Singleton
    fun visitanteRetrofit(@ApplicationContext appContext: Context): VisitanteApi {
        return AppRetrofit.getInstance(appContext).create(VisitanteApi::class.java)
    }

}