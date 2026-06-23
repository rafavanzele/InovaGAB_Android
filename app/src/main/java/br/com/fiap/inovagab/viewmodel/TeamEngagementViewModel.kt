package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.TeamEngagement
import br.com.fiap.inovagab.data.repository.TeamEngagementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TeamEngagementViewModel : ViewModel() {

    private val repository = TeamEngagementRepository()

    private val _teamEngagementData = MutableStateFlow<List<TeamEngagement>>(emptyList())
    val teamEngagementData: StateFlow<List<TeamEngagement>> = _teamEngagementData

    init {
        loadTeamEngagementData()
    }

    private fun loadTeamEngagementData() {
        _teamEngagementData.value = repository.getTeamEngagementData()
    }
}