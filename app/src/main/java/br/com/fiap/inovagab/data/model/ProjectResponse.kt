package br.com.fiap.inovagab.data.remote.model

data class ProjectResponse(
    val id: String,
    val titulo: String,
    val descricao: String,
    val diretrizId: String,
    val responsavel: String,
    val status: String,
    val etapa: String,
    val prazo: String,
    val investimento: String,
    val investimentoValor: Double?,
    val retornoPrevisto: String,
    val resultado: String,
    val progresso: Float,
    val dataCriacao: String,
    val gestorId: String
)