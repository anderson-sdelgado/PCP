package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UpdateTableModule {

    @Binds
    @Singleton
    fun bindUpdateChave(usecase: IUpdateTableChave): UpdateTableChave

    @Binds
    @Singleton
    fun bindUpdateColab(usecase: IUpdateTableColab): UpdateTableColab

    @Binds
    @Singleton
    fun bindUpdateEquip(usecase: IUpdateTableEquip): UpdateTableEquip

    @Binds
    @Singleton
    fun bindUpdateFluxo(usecase: IUpdateTableFluxo): UpdateTableFluxo

    @Binds
    @Singleton
    fun bindUpdateLocal(usecase: IUpdateLocal): UpdateTableLocal

    @Binds
    @Singleton
    fun bindUpdateLocalTrab(usecase: IUpdateTableLocalTrab): UpdateTableLocalTrab

    @Binds
    @Singleton
    fun bindUpdateRLocalFluxo(usecase: IUpdateTableRLocalFluxo): UpdateTableRLocalFluxo

    @Binds
    @Singleton
    fun bindUpdateTerceiro(usecase: IUpdateTableTerceiro): UpdateTableTerceiro

    @Binds
    @Singleton
    fun bindUpdateTerceiro(usecase: IUpdateTableVisitante): UpdateTableVisitante

}
