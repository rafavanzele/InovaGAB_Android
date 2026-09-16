package br.com.fiap.inovagab.data.remote.model

data class CreateProjectRequest(
    val titulo: String,
    val descricao: String,
    val diretrizId: String,
    val responsavel: String,
    val prazo: String,
    val investimento: String,
    val investimentoValor: Double?,
    val retornoPrevisto: String
)