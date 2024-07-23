package br.com.usinasantafe.pcp.utils

data class ResultUpdateDatabase(
    val count: Int,
    val describe: String,
    val size: Int,
    var percentage: Int = ((count * 100) / size)
)
