package br.com.fiap.inovagab.data.mock

import br.com.fiap.inovagab.data.model.TeamEngagement

object TeamEngagementMock {

    val teamEngagementData = listOf(

        TeamEngagement(
            id = 1,
            title = "Colaboradores participantes",
            value = "128",
            description = "Pessoas envolvidas em ideias e iniciativas",
            iconName = "groups"
        ),

        TeamEngagement(
            id = 2,
            title = "Novos participantes",
            value = "24",
            description = "Colaboradores que começaram a participar este mês",
            iconName = "person_add"
        ),

        TeamEngagement(
            id = 3,
            title = "Times engajados",
            value = "9",
            description = "Equipes com participação ativa",
            iconName = "team"
        ),

        TeamEngagement(
            id = 4,
            title = "Destaques do mês",
            value = "6",
            description = "Iniciativas com maior impacto no período",
            iconName = "star"
        )
    )
}