package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.StrategicIndicator
import br.com.fiap.inovagab.data.repository.StrategicIndicatorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.data.remote.model.CreateStrategicIndicatorRequest

class StrategicIndicatorViewModel : ViewModel() {

    private val repository = StrategicIndicatorRepository()

    private val _indicators =
        MutableStateFlow<List<StrategicIndicator>>(emptyList())
    val indicators: StateFlow<List<StrategicIndicator>> = _indicators

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadIndicators(token: String) {
        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = repository.getIndicators(token)
                _indicators.value = response

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao carregar indicadores estratégicos."

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun createIndicator(
        token: String,
        request: CreateStrategicIndicatorRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            try {
                repository.createIndicator(
                    token = token,
                    request = request
                )

                loadIndicators(token)

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Erro ao criar indicador estratégico."
                )
            }
        }
    }

    fun updateIndicator(
        token: String,
        id: String,
        request: CreateStrategicIndicatorRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            try {
                repository.updateIndicator(
                    token = token,
                    id = id,
                    request = request
                )

                loadIndicators(token)

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Erro ao atualizar indicador estratégico."
                )
            }
        }
    }

    fun deleteIndicator(
        token: String,
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            try {
                repository.deleteIndicator(
                    token = token,
                    id = id
                )

                loadIndicators(token)

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Erro ao excluir indicador estratégico."
                )
            }
        }
    }
}