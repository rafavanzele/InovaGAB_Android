package br.com.fiap.inovagab.data.remote.model

data class TeamResponse(
    val id: String,
    val nome: String,
    val descricao: String,
    val responsavel: String,
    val membros: List<String>,
    val projetoId: String?,
    val gestorId: String,
    val dataCriacao: String
)