package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.Project
import br.com.fiap.inovagab.data.repository.ProjectRepository
import kotlinx.coroutines.flow.StateFlow

class ProjectViewModel : ViewModel() {

    private val repository = ProjectRepository()

    val projects: StateFlow<List<Project>> = repository.projects

    fun addProject(project: Project) {

        repository.addProject(project)

    }
}