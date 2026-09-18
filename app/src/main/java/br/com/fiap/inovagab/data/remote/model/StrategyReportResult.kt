package br.com.fiap.inovagab.data.remote.model

data class StrategyReportResult(
    val diretrizId: String,
    val diretrizTitulo: String,
    val categoria: String,
    val campanha: String,
    val totalProjetos: Int,
    val totalResultados: Int,
    val projetos: List<ProjectReportResult>
)