package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import kotlinx.coroutines.flow.Flow

interface EquipRepository {

    suspend fun addAllEquip(list: List<Equip>)

    suspend fun checkEquipNro(nro: Long): Boolean

    suspend fun deleteAllEquip()

    suspend fun getEquipId(id: Long): Equip

    suspend fun getEquipNro(nro: Long): Equip

    suspend fun recoverAllEquip(token: String): Flow<Result<List<Equip>>>

}