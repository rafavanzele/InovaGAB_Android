package br.com.fiap.inovagab.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.Idea
import br.com.fiap.inovagab.data.model.IdeaStatus
import br.com.fiap.inovagab.data.repository.IdeaRepository
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.CreateIdeaRequest
import br.com.fiap.inovagab.data.remote.model.IdeaResponse
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import br.com.fiap.inovagab.data.remote.model.UpdateIdeaRequest

class IdeaViewModel : ViewModel() {

    private val repository = IdeaRepository()

    val ideas = repository.ideas

    private val _remoteIdeas =
        MutableStateFlow<List<IdeaResponse>>(emptyList())

    val remoteIdeas: StateFlow<List<IdeaResponse>> =
        _remoteIdeas

    fun addIdea(idea: Idea) {
        repository.addIdea(idea)
    }

    fun createIdea(
        token: String,
        title: String,
        description: String,
        category: String,
        strategicGuidanceId: String,
        onSuccess: (IdeaResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.ideaApi.createIdea(
                    authorization = "Bearer $token",
                    request = CreateIdeaRequest(
                        titulo = title.trim(),
                        descricao = description.trim(),
                        categoria = category.trim(),
                        diretrizId = strategicGuidanceId
                    )
                )

                onSuccess(response)

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível cadastrar a ideia."
                )
            }
        }
    }

    fun loadMyIdeas(
        token: String,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _remoteIdeas.value =
                    RetrofitInstance.ideaApi.getMyIdeas(
                        authorization = "Bearer $token"
                    )
            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível carregar suas ideias."
                )
            }
        }
    }

    fun updateIdea(
        token: String,
        id: String,
        title: String,
        description: String,
        category: String,
        onSuccess: (IdeaResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.ideaApi.updateIdea(
                    id = id,
                    authorization = "Bearer $token",
                    request = UpdateIdeaRequest(
                        titulo = title.trim(),
                        descricao = description.trim(),
                        categoria = category.trim()
                    )
                )

                onSuccess(response)

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível atualizar a ideia."
                )
            }
        }
    }
    fun updateIdeaStatus(
        ideaId: Int,
        newStatus: IdeaStatus
    ) {
        repository.updateIdeaStatus(
            ideaId = ideaId,
            newStatus = newStatus
        )
    }

    fun deleteIdea(
        token: String,
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.ideaApi.deleteIdea(
                    id = id,
                    authorization = "Bearer $token"
                )

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Não foi possível excluir a ideia.")
                }

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível excluir a ideia."
                )
            }
        }
    }
}
