package br.com.fiap.inovagab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.CreateTeamRequest
import br.com.fiap.inovagab.data.remote.model.TeamResponse
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    private val _teams =
        MutableStateFlow<List<TeamResponse>>(emptyList())

    val teams: StateFlow<List<TeamResponse>> =
        _teams

    fun loadTeams(
        token: String,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _teams.value =
                    RetrofitInstance.teamApi.getTeams(
                        authorization = "Bearer $token"
                    )
            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível carregar as equipes."
                )
            }
        }
    }

    fun createTeam(
        token: String,
        request: CreateTeamRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.teamApi.createTeam(
                    authorization = "Bearer $token",
                    request = request
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível criar a equipe."
                )
            }
        }
    }

    fun updateTeam(
        token: String,
        id: String,
        request: CreateTeamRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.teamApi.updateTeam(
                    authorization = "Bearer $token",
                    id = id,
                    request = request
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível atualizar a equipe."
                )
            }
        }
    }

    fun deleteTeam(
        token: String,
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.teamApi.deleteTeam(
                    authorization = "Bearer $token",
                    id = id
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível excluir a equipe."
                )
            }
        }
    }
}