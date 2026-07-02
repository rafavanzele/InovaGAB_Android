package br.com.fiap.inovagab.data.mock

import br.com.fiap.inovagab.data.model.AchievedResult

object AchievedResultMock {

    val achievedResults = listOf(
        AchievedResult(
            id = "1",
            title = "Ideias implementadas",
            value = "87",
            description = "Soluções colocadas em prática nas áreas",
            iconName = "check"
        ),

        AchievedResult(
            id = "2",
            title = "Economia gerada",
            value = "R$ 120 mil",
            description = "Redução estimada de custos operacionais",
            iconName = "money"
        ),

        AchievedResult(
            id = "3",
            title = "Processos otimizados",
            value = "15",
            description = "Fluxos internos melhorados por inovação",
            iconName = "speed"
        ),

        AchievedResult(
            id = "4",
            title = "Reconhecimentos internos",
            value = "32",
            description = "Colaboradores reconhecidos por contribuições",
            iconName = "award"
        )
    )
}