package br.com.usinasantafe.pcp.di.external.room

import br.com.usinasantafe.pcp.external.room.DatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.stable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRoomModule {

    @Provides
    @Singleton
    fun provideChaveDao(database: DatabaseRoom): ChaveDao {
        return database.chaveDao()
    }

    @Provides
    @Singleton
    fun provideColabDao(database: DatabaseRoom): ColabDao {
        return database.colabDao()
    }

    @Provides
    @Singleton
    fun provideEquipDao(database: DatabaseRoom): EquipDao {
        return database.equipDao()
    }

    @Provides
    @Singleton
    fun provideFluxoDao(database: DatabaseRoom): FluxoDao {
        return database.fluxoDao()
    }

    @Provides
    @Singleton
    fun provideLocalDao(database: DatabaseRoom): LocalDao {
        return database.localDao()
    }

    @Provides
    @Singleton
    fun provideLocalTrabDao(database: DatabaseRoom): LocalTrabDao {
        return database.localTrabDao()
    }

    @Provides
    @Singleton
    fun provideRLocalFluxoDao(database: DatabaseRoom): RLocalFluxoDao {
        return database.rLocalFluxoDao()
    }

    @Provides
    @Singleton
    fun provideTerceiroDao(database: DatabaseRoom): TerceiroDao {
        return database.terceiroDao()
    }

    @Provides
    @Singleton
    fun provideVisitanteDao(database: DatabaseRoom): VisitanteDao {
        return database.visitanteDao()
    }

}
