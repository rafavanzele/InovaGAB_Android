package br.com.fiap.inovagab.data.remote.model

data class StrategicIndicator(
    val id: String = "",
    val titulo: String = "",
    val descricao: String = "",
    val valorAtual: Double = 0.0,
    val meta: Double = 0.0,
    val unidade: String = "",
    val status: String = "",
    val dataAtualizacao: String = ""
)