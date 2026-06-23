package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.StrategicIndicator
import br.com.fiap.inovagab.data.repository.StrategicIndicatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StrategicIndicatorViewModel : ViewModel() {

    private val repository = StrategicIndicatorRepository()

    private val _indicators =
        MutableStateFlow<List<StrategicIndicator>>(emptyList())
    val indicators: StateFlow<List<StrategicIndicator>> = _indicators

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadIndicators()
    }

    private fun loadIndicators() {
        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = repository.getIndicators()
                _indicators.value = response

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao carregar indicadores estratégicos."

            } finally {

                _isLoading.value = false
            }
        }
    }
}