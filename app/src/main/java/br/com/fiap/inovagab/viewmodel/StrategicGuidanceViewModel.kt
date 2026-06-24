package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import br.com.fiap.inovagab.data.repository.StrategicGuidanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StrategicGuidanceViewModel : ViewModel() {

    private val repository = StrategicGuidanceRepository()

    private val _guidances =
        MutableStateFlow<List<StrategicGuidance>>(emptyList())

    val guidances: StateFlow<List<StrategicGuidance>>
            = _guidances

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean>
            = _isLoading

    private val _errorMessage =
        MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?>
            = _errorMessage

    init {
        loadGuidances()
    }

    fun loadGuidances() {

        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null

            try {

                _guidances.value =
                    repository.getGuidances()

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao carregar orientações."

            } finally {

                _isLoading.value = false

            }
        }
    }

    fun createGuidance(
        strategicGuidance: StrategicGuidance
    ) {

        viewModelScope.launch {

            try {

                repository.createGuidance(
                    strategicGuidance
                )

                loadGuidances()

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao cadastrar orientação"

            }
        }
    }

    fun updateGuidance(
        strategicGuidance: StrategicGuidance
    ) {

        if (strategicGuidance.id.isBlank()) {
            _errorMessage.value = "Não foi possível editar esta orientação."
            return
        }

        viewModelScope.launch {

            try {

                repository.updateGuidance(
                    strategicGuidance.id,
                    strategicGuidance
                )

                loadGuidances()

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao atualizar orientação"

            }
        }
    }

    fun deleteGuidance(
        id: String
    ) {

        if (id.isBlank()) {
            _errorMessage.value = "Não foi possível excluir esta orientação."
            return
        }

        viewModelScope.launch {

            try {

                repository.deleteGuidance(id)

                loadGuidances()

            } catch (e: Throwable) {

                _errorMessage.value =
                    "Erro ao excluir orientação"

            }
        }
    }
}