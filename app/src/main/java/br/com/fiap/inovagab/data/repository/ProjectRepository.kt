package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProjectRepository {

    private val _projects = MutableStateFlow(

        listOf(

            Project(
                id = 1,
                title = "Dashboard Corporativo",
                description = "Painel para acompanhamento de indicadores.",
                responsible = "Equipe UX",
                status = "Em andamento",
                deadline = "15/06/2026",
                investment = "R$ 10.000",
                expectedReturn = "R$ 35.000",
                result = "Redução de 20% no tempo operacional",
                progress = 0.80f
            ),

            Project(
                id = 2,
                title = "Programa de Inovação",
                description = "Programa interno de ideias.",
                responsible = "Equipe Estratégica",
                status = "Planejamento",
                deadline = "20/07/2026",
                investment = "R$ 8.000",
                expectedReturn = "R$ 20.000",
                result = "Em análise",
                progress = 0.55f
            ),

            Project(
                id = 3,
                title = "Portal Interno",
                description = "Portal para comunicação corporativa.",
                responsible = "Equipe Desenvolvimento",
                status = "Iniciado",
                deadline = "30/08/2026",
                investment = "R$ 15.000",
                expectedReturn = "R$ 40.000",
                result = "Em andamento",
                progress = 0.35f
            )
        )
    )

    val projects: StateFlow<List<Project>> = _projects

    fun addProject(project: Project) {

        _projects.value += project

    }
}