package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import br.com.fiap.inovagab.data.repository.StrategicGuidanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import br.com.fiap.inovagab.data.remote.model.CreateStrategicGuidanceRequest

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

    fun loadStrategicGuidances(token: String) {

        viewModelScope.launch {

            _isLoading.value = true
            _errorMessage.value = null

            try {

                _guidances.value =
                    RetrofitInstance.api.getStrategicGuidances(
                        authorization = "Bearer $token"
                    )

            } catch (e: Exception) {

                _errorMessage.value =
                    e.message ?: "Erro ao carregar diretrizes estratégicas."

            } finally {

                _isLoading.value = false

            }
        }
    }

    fun createGuidance(
        token: String,
        request: CreateStrategicGuidanceRequest
    ) {

        viewModelScope.launch {

            try {

                repository.createGuidance(
                    token,
                    request
                )

                loadStrategicGuidances(token)

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao cadastrar orientação"
            }
        }
    }

    fun updateGuidance(
        token: String,
        id: String,
        request: CreateStrategicGuidanceRequest
    ) {

        if (id.isBlank()) {
            _errorMessage.value =
                "Não foi possível editar esta orientação."
            return
        }

        viewModelScope.launch {

            try {

                repository.updateGuidance(
                    token,
                    id,
                    request
                )

                loadStrategicGuidances(token)

            } catch (e: Exception) {

                _errorMessage.value =
                    "Erro ao atualizar orientação"
            }
        }
    }

    fun deleteGuidance(
        token: String,
        id: String
    ) {

        if (id.isBlank()) {
            _errorMessage.value =
                "Não foi possível excluir esta orientação."
            return
        }

        viewModelScope.launch {

            try {

                repository.deleteGuidance(
                    token,
                    id
                )

                loadStrategicGuidances(token)

            } catch (e: Throwable) {

                _errorMessage.value =
                    "Erro ao excluir orientação"
            }
        }
    }
}