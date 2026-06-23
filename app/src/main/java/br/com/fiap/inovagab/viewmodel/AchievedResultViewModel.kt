package br.com.fiap.inovagab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.model.AchievedResult
import br.com.fiap.inovagab.data.repository.AchievedResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AchievedResultViewModel : ViewModel() {

    private val repository = AchievedResultRepository()

    private val _achievedResults = MutableStateFlow<List<AchievedResult>>(emptyList())
    val achievedResults: StateFlow<List<AchievedResult>> = _achievedResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadAchievedResults()
    }

    fun loadAchievedResults() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                _achievedResults.value = repository.getAchievedResults()

            } catch (e: Exception) {
                _errorMessage.value = "Não foi possível carregar os resultados alcançados."
            } finally {
                _isLoading.value = false
            }
        }
    }
}