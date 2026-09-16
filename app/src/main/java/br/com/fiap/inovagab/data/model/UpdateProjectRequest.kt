package br.com.fiap.inovagab.data.remote.model

data class UpdateProjectRequest(
    val titulo: String,
    val descricao: String,
    val responsavel: String,
    val prazo: String,
    val investimento: String,
    val investimentoValor: Double?,
    val retornoPrevisto: String,
    val status: String,
    val etapa: String,
    val progresso: Float
)