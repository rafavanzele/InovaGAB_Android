package br.com.fiap.inovagab.data.remote.model

data class ProjectReportResult(
    val projetoId: String,
    val projetoTitulo: String,
    val diretrizId: String,
    val status: String,
    val prazo: String,
    val investimento: String,
    val retornoPrevisto: String,
    val progresso: Float,
    val resultados: List<AchievedResultResponse>
)