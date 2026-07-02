package br.com.fiap.inovagab.data.mock

import br.com.fiap.inovagab.data.model.Team

object TeamMock {

    val teams = listOf(
        Team(
            name = "Equipe Operacional",
            members = 12,
            ideasSubmitted = 8
        ),

        Team(
            name = "Equipe Estratégica",
            members = 6,
            ideasSubmitted = 5
        ),

        Team(
            name = "Equipe Desenvolvimento",
            members = 9,
            ideasSubmitted = 11
        )
    )
}