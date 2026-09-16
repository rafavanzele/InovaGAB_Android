package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.Project
import br.com.fiap.inovagab.data.repository.ProjectRepository
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.ProjectResponse
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.data.remote.model.CreateProjectRequest
import br.com.fiap.inovagab.data.remote.model.UpdateProjectRequest

class ProjectViewModel : ViewModel() {

    private val repository = ProjectRepository()

    val projects: StateFlow<List<Project>> = repository.projects

    private val _remoteProjects =
        MutableStateFlow<List<ProjectResponse>>(emptyList())

    val remoteProjects: StateFlow<List<ProjectResponse>> =
        _remoteProjects

    fun addProject(project: Project) {

        repository.addProject(project)

    }

    fun loadProjects(
        token: String,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _remoteProjects.value =
                    RetrofitInstance.projectApi.getProjects(
                        authorization = "Bearer $token"
                    )
            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível carregar os projetos."
                )
            }
        }
    }

    fun createRemoteProject(
        token: String,
        request: CreateProjectRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.projectApi.createProject(
                    authorization = "Bearer $token",
                    request = request
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível criar o projeto."
                )
            }
        }
    }

    fun updateRemoteProject(
        token: String,
        id: String,
        request: UpdateProjectRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.projectApi.updateProject(
                    authorization = "Bearer $token",
                    id = id,
                    request = request
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível atualizar o projeto."
                )
            }
        }
    }
}