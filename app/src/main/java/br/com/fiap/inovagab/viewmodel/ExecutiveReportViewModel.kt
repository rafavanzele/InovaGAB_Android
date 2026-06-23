package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.ExecutiveReport
import br.com.fiap.inovagab.data.repository.ExecutiveReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExecutiveReportViewModel : ViewModel() {

    private val repository = ExecutiveReportRepository()

    private val _executiveReports = MutableStateFlow<List<ExecutiveReport>>(emptyList())
    val executiveReports: StateFlow<List<ExecutiveReport>> = _executiveReports

    init {
        loadExecutiveReports()
    }

    private fun loadExecutiveReports() {
        _executiveReports.value = repository.getExecutiveReports()
    }
}