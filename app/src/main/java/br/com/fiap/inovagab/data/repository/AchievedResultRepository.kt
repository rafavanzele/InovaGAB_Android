package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.model.AchievedResult
import br.com.fiap.inovagab.data.mock.AchievedResultMock

class AchievedResultRepository {

    suspend fun getAchievedResults(): List<AchievedResult> {
        return AchievedResultMock.achievedResults
    }
}