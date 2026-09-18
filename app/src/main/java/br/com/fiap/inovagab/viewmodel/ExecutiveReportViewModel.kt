package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.ExecutiveReportResponse
import br.com.fiap.inovagab.data.repository.ExecutiveReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExecutiveReportViewModel : ViewModel() {

    private val repository = ExecutiveReportRepository()

    private val _executiveReport =
        MutableStateFlow<ExecutiveReportResponse?>(null)

    val executiveReport: StateFlow<ExecutiveReportResponse?> =
        _executiveReport

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadExecutiveReport(token: String) {

        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null

            try {

                _executiveReport.value =
                    repository.getExecutiveReport(token)

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao carregar relatório executivo."

            } finally {

                _isLoading.value = false
            }
        }
    }
}