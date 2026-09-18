package br.com.fiap.inovagab.data.remote.model

data class ExecutiveReportResponse(
    val totalIdeias: Int,
    val ideiasPendentes: Int,
    val ideiasAprovadas: Int,
    val ideiasRejeitadas: Int,
    val totalProjetos: Int,
    val projetosEmAndamento: Int,
    val projetosConcluidos: Int,
    val totalEquipes: Int,
    val totalDiretrizesEstrategicas: Int,
    val totalIndicadoresEstrategicos: Int,
    val totalResultadosAlcancados: Int,
    val investimentoTotalProjetos: Double,
    val retornoFinanceiroTotal: Double,
    val lucroObtido: Double,
    val roiPercentual: Double?,
    val mediaEngajamentoEquipes: Double,
    val mediaAumentoProdutividadePercentual: Double?,
    val resultadosPorProjeto: List<ProjectReportResult>,
    val resultadosPorEstrategia: List<StrategyReportResult>,
    val dataGeracao: String
)