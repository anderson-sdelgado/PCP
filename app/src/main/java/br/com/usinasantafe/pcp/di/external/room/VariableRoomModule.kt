package br.com.usinasantafe.pcp.di.external.room

import br.com.usinasantafe.pcp.external.room.DatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.variable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRoomModule {

    @Provides
    @Singleton
    fun provideMovChaveDao(database: DatabaseRoom): MovChaveDao {
        return database.movChaveDao()
    }

    @Provides
    @Singleton
    fun provideMovChaveEquipDao(database: DatabaseRoom): MovChaveEquipDao {
        return database.movChaveEquipDao()
    }

    @Provides
    @Singleton
    fun provideMovEquipProprioDao(database: DatabaseRoom): MovEquipProprioDao {
        return database.movEquipProprioDao()
    }

    @Provides
    @Singleton
    fun provideEquipProprioPassagDao(database: DatabaseRoom): MovEquipProprioPassagDao {
        return database.movEquipProprioPassagDao()
    }

    @Provides
    @Singleton
    fun provideEquipProprioEquipSegDao(database: DatabaseRoom): MovEquipProprioEquipSegDao {
        return database.movEquipProprioEquipSegDao()
    }

    @Provides
    @Singleton
    fun provideEquipVisitTercDao(database: DatabaseRoom): MovEquipVisitTercDao {
        return database.movEquipVisitTercDao()
    }

    @Provides
    @Singleton
    fun provideEquipVisitTercPassagDao(database: DatabaseRoom): MovEquipVisitTercPassagDao {
        return database.movEquipVisitTercPassagDao()
    }

    @Provides
    @Singleton
    fun provideEquipResidenciaDao(database: DatabaseRoom): MovEquipResidenciaDao {
        return database.movEquipResidenciaDao()
    }

}
