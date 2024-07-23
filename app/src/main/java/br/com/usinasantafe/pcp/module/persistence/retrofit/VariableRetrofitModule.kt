package br.com.usinasantafe.pcp.module.persistence.retrofit

import android.content.Context
import br.com.usinasantafe.pcp.external.webservices.AppRetrofit
import br.com.usinasantafe.pcp.external.webservices.api.variable.ConfigApi
import br.com.usinasantafe.pcp.external.webservices.api.variable.MovEquipProprioApi
import br.com.usinasantafe.pcp.external.webservices.api.variable.MovEquipResidenciaApi
import br.com.usinasantafe.pcp.external.webservices.api.variable.MovEquipVisitTercApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRetrofitModule {

    @Provides
    @Singleton
    fun configApiRetrofit(@ApplicationContext appContext: Context): ConfigApi {
        return AppRetrofit.getInstance(appContext).create(ConfigApi::class.java)
    }

    @Provides
    @Singleton
    fun movEquipProprioApiRetrofit(@ApplicationContext appContext: Context): MovEquipProprioApi {
        return AppRetrofit.getInstance(appContext).create(MovEquipProprioApi::class.java)
    }

    @Provides
    @Singleton
    fun movEquipVisitTercApiRetrofit(@ApplicationContext appContext: Context): MovEquipVisitTercApi {
        return AppRetrofit.getInstance(appContext).create(MovEquipVisitTercApi::class.java)
    }

    @Provides
    @Singleton
    fun movEquipResidenciaApiRetrofit(@ApplicationContext appContext: Context): MovEquipResidenciaApi {
        return AppRetrofit.getInstance(appContext).create(MovEquipResidenciaApi::class.java)
    }

}