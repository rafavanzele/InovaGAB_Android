package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import br.com.fiap.inovagab.data.mock.ProjectMock

class ProjectRepository {

    private val _projects = MutableStateFlow(
        ProjectMock.projects
    )

    val projects: StateFlow<List<Project>> = _projects

    fun addProject(project: Project) {

        _projects.value += project

    }
}