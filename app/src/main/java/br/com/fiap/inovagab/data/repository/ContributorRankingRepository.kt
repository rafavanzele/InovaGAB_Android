package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.model.ContributorRanking

class ContributorRankingRepository {

    fun getRanking(): List<ContributorRanking> {
        return listOf(
            ContributorRanking(
                id = 1,
                nome = "Ana Souza",
                cargo = "Operadora",
                ideiasEnviadas = 12,
                ideiasAprovadas = 5,
                badge = "Top Inovadora"
            ),
            ContributorRanking(
                id = 2,
                nome = "Carlos Lima",
                cargo = "Operador",
                ideiasEnviadas = 9,
                ideiasAprovadas = 4,
                badge = "Ideias de Impacto"
            ),
            ContributorRanking(
                id = 3,
                nome = "Mariana Alves",
                cargo = "Operadora",
                ideiasEnviadas = 7,
                ideiasAprovadas = 3,
                badge = "Colaboração Ativa"
            )
        )
    }
}