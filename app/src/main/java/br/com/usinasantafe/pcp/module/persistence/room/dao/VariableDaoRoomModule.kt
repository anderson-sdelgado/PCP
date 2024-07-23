package br.com.usinasantafe.pcp.module.persistence.room.dao

import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.variable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableDaoRoomModule {

    @Provides
    @Singleton
    fun provideMovEquipProprioDao(database: AppDatabaseRoom): MovEquipProprioDao {
        return database.movEquipProprioDao()
    }

    @Provides
    @Singleton
    fun provideMovEquipProprioSegDao(database: AppDatabaseRoom): MovEquipProprioSegDao {
        return database.movEquipProprioSegDao()
    }

    @Provides
    @Singleton
    fun provideMovEquipProprioPassagDao(database: AppDatabaseRoom): MovEquipProprioPassagDao {
        return database.movEquipProprioPassagDao()
    }

    @Provides
    @Singleton
    fun provideMovEquipVisitTercDao(database: AppDatabaseRoom): MovEquipVisitTercDao {
        return database.movEquipVisitTercDao()
    }

    @Provides
    @Singleton
    fun provideMovEquipVisitTercPassagDao(database: AppDatabaseRoom): MovEquipVisitTercPassagDao {
        return database.movEquipVisitTercPassagDao()
    }

    @Provides
    @Singleton
    fun provideMovEquipResidenciaDao(database: AppDatabaseRoom): MovEquipResidenciaDao {
        return database.movEquipResidenciaDao()
    }

}