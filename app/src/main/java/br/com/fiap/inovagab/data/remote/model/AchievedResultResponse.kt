package br.com.fiap.inovagab.data.remote.model

data class AchievedResultResponse(
    val id: String? = null,
    val titulo: String = "",
    val descricao: String = "",
    val categoria: String = "",
    val projetoId: String = "",
    val valorAlcancado: Double = 0.0,
    val unidade: String = "",
    val dataResultado: String = "",
    val dataRegistro: String = ""
)