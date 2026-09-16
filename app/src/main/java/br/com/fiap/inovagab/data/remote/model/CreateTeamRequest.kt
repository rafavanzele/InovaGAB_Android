package br.com.fiap.inovagab.data.remote.model

data class CreateTeamRequest(
    val nome: String,
    val descricao: String,
    val responsavel: String,
    val membros: List<String>,
    val projetoId: String?
)