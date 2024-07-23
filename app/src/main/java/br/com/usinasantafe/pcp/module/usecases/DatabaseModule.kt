package br.com.usinasantafe.pcp.module.usecases

import br.com.usinasantafe.pcp.domain.usecases.database.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {

    @Singleton
    @Binds
    fun bindUpdateDataBase(usecase: UpdateAllDataBaseImpl): UpdateAllDataBase

    @Singleton
    @Binds
    fun bindUpdateColab(usecase: UpdateColabImpl): UpdateColab

    @Singleton
    @Binds
    fun bindUpdateEquip(usecase: UpdateEquipImpl): UpdateEquip

    @Singleton
    @Binds
    fun bindUpdateLocal(usecase: UpdateLocalImpl): UpdateLocal

    @Singleton
    @Binds
    fun bindUpdateTerceiro(usecase: UpdateTerceiroImpl): UpdateTerceiro

    @Singleton
    @Binds
    fun bindUpdateVisitante(usecase: UpdateVisitanteImpl): UpdateVisitante

    @Singleton
    @Binds
    fun bindUpdateVisitTerc(usecase: UpdateVisitTercImpl): UpdateVisitTerc

}