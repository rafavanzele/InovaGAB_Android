package br.com.fiap.inovagab.data.remote.model

data class CreateAchievedResultRequest(
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val projetoId: String,
    val valorAlcancado: Double,
    val unidade: String,
    val dataResultado: String
)