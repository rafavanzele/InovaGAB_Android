package br.com.fiap.inovagab.data.remote.model

data class CreateStrategicGuidanceRequest(
    val titulo: String,
    val descricao: String,
    val objetivo: String,
    val responsavel: String,
    val categoria: String,
    val campanha: String,
    val status: String = "Ativa"
)