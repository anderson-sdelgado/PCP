package br.com.usinasantafe.pcp.module.persistence.room.dao

import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.room.dao.stable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableDaoRoomModule {

    @Provides
    @Singleton
    fun provideColabDao(database: AppDatabaseRoom): ColabDao {
        return database.colabDao()
    }

    @Provides
    @Singleton
    fun provideEquipDao(database: AppDatabaseRoom): EquipDao {
        return database.equipDao()
    }

    @Provides
    @Singleton
    fun provideLocalDao(database: AppDatabaseRoom): LocalDao {
        return database.localDao()
    }

    @Provides
    @Singleton
    fun provideTerceiroDao(database: AppDatabaseRoom): TerceiroDao {
        return database.terceiroDao()
    }

    @Provides
    @Singleton
    fun provideVisitanteDao(database: AppDatabaseRoom): VisitanteDao {
        return database.visitanteDao()
    }

}