package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.mock.TeamEngagementMock
import br.com.fiap.inovagab.data.model.TeamEngagement

class TeamEngagementRepository {

    fun getTeamEngagementData(): List<TeamEngagement> {
        return TeamEngagementMock.teamEngagementData
    }
}