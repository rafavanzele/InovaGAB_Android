package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance

class StrategicGuidanceRepository {

    suspend fun getGuidances(): List<StrategicGuidance> {
        return RetrofitInstance.api.getGuidances()
    }

    suspend fun createGuidance(
        strategicGuidance: StrategicGuidance
    ): StrategicGuidance {
        return RetrofitInstance.api.createGuidance(strategicGuidance)
    }

    suspend fun updateGuidance(
        id: String,
        strategicGuidance: StrategicGuidance
    ): StrategicGuidance {
        return RetrofitInstance.api.updateGuidance(id, strategicGuidance)
    }

    suspend fun deleteGuidance(id: String) {
        RetrofitInstance.api.deleteGuidance(id)
    }
}