package br.com.fiap.inovagab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.AchievedResultResponse
import br.com.fiap.inovagab.data.remote.model.CreateAchievedResultRequest
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RemoteAchievedResultViewModel : ViewModel() {

    private val _achievedResults =
        MutableStateFlow<List<AchievedResultResponse>>(emptyList())

    val achievedResults: StateFlow<List<AchievedResultResponse>> =
        _achievedResults

    fun loadAchievedResults(
        token: String,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _achievedResults.value =
                    RetrofitInstance.achievedResultApi.getAchievedResults(
                        authorization = "Bearer $token"
                    )
            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível carregar os resultados alcançados."
                )
            }
        }
    }

    fun createAchievedResult(
        token: String,
        request: CreateAchievedResultRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.achievedResultApi.createAchievedResult(
                    authorization = "Bearer $token",
                    request = request
                )

                onSuccess()

            } catch (e: Exception) {
                onError(
                    e.message ?: "Não foi possível registrar o resultado."
                )
            }
        }
    }
}