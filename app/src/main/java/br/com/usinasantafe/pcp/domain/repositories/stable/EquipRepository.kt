package br.com.usinasantafe.pcp.domain.repositories.stable

import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.utils.EmptyResult

interface EquipRepository {
    suspend fun addAll(list: List<Equip>): EmptyResult
    suspend fun hasNro(nroEquip: Long): Result<Boolean>
    suspend fun deleteAll(): EmptyResult
    suspend fun getById(idEquip: Int): Result<Equip>
    suspend fun getIdByNro(nroEquip: Long): Result<Int>
    suspend fun getNroById(idEquip: Int): Result<Long>
    suspend fun getDescrById(idEquip: Int): Result<String>
    suspend fun listAll(token: String): Result<List<Equip>>
}