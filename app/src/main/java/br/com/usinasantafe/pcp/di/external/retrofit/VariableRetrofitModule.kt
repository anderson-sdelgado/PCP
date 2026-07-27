package br.com.usinasantafe.pcp.di.external.retrofit

import br.com.usinasantafe.pcp.external.retrofit.api.variable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRetrofitModule {

    @Provides
    @Singleton
    fun configApiRetrofit(
        retrofit: Retrofit
    ): ConfigApi = retrofit.create(ConfigApi::class.java)

    @Provides
    @Singleton
    fun movChaveApiRetrofit(
        retrofit: Retrofit
    ): MovChaveApi = retrofit.create(MovChaveApi::class.java)

    @Provides
    @Singleton
    fun movChaveEquipApiRetrofit(
        retrofit: Retrofit
    ): MovChaveEquipApi = retrofit.create(MovChaveEquipApi::class.java)

    @Provides
    @Singleton
    fun movEquipProprioApiRetrofit(
        retrofit: Retrofit
    ): MovEquipProprioApi = retrofit.create(MovEquipProprioApi::class.java)

    @Provides
    @Singleton
    fun movEquipVisitTercApiRetrofit(
        retrofit: Retrofit
    ): MovEquipVisitTercApi = retrofit.create(MovEquipVisitTercApi::class.java)

    @Provides
    @Singleton
    fun movEquipResidenciaApiRetrofit(
        retrofit: Retrofit
    ): MovEquipResidenciaApi = retrofit.create(MovEquipResidenciaApi::class.java)

}
