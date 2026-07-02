package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.mock.ContributorRankingMock
import br.com.fiap.inovagab.data.model.ContributorRanking

class ContributorRankingRepository {

    fun getRanking(): List<ContributorRanking> {
        return ContributorRankingMock.ranking
    }
}