package br.com.fiap.inovagab.data.mock

import br.com.fiap.inovagab.data.model.ExecutiveReport

object ExecutiveReportMock {

    val executiveReports = listOf(

        ExecutiveReport(
            id = 1,
            title = "Crescimento das iniciativas",
            description = "Aumento no número de ideias cadastradas e acompanhadas.",
            metric = "+18%",
            iconName = "growth"
        ),

        ExecutiveReport(
            id = 2,
            title = "Taxa de aprovação",
            description = "Percentual de propostas aprovadas no ciclo atual.",
            metric = "92%",
            iconName = "approval"
        ),

        ExecutiveReport(
            id = 3,
            title = "Economia operacional",
            description = "Estimativa de redução de custos gerada pelas iniciativas.",
            metric = "R$120k",
            iconName = "savings"
        ),

        ExecutiveReport(
            id = 4,
            title = "Satisfação interna",
            description = "Avaliação média dos colaboradores participantes.",
            metric = "4.8",
            iconName = "satisfaction"
        )
    )
}