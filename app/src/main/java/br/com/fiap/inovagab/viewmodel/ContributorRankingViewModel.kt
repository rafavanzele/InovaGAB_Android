package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.ContributorRanking
import br.com.fiap.inovagab.data.repository.ContributorRankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContributorRankingViewModel : ViewModel() {

    private val repository = ContributorRankingRepository()

    private val _ranking =
        MutableStateFlow<List<ContributorRanking>>(emptyList())

    val ranking: StateFlow<List<ContributorRanking>> = _ranking

    init {
        loadRanking()
    }

    private fun loadRanking() {
        _ranking.value =
            repository.getRanking()
    }
}