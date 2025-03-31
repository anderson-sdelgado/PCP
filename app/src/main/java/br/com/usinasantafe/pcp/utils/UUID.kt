package br.com.usinasantafe.pcp.utils

import java.util.UUID

object UUIDProvider {
    fun uuid(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }
}