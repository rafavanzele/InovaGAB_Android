package br.com.fiap.inovagab.data.remote.model

data class CreateStrategicIndicatorRequest(
    val titulo: String,
    val descricao: String,
    val valorAtual: Double,
    val meta: Double,
    val unidade: String,
    val status: String = "Em acompanhamento"
)