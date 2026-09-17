package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import br.com.fiap.inovagab.data.remote.model.CreateStrategicGuidanceRequest

class StrategicGuidanceRepository {

    suspend fun getGuidances(): List<StrategicGuidance> {
        return RetrofitInstance.api.getGuidances()
    }

    suspend fun createGuidance(
        token: String,
        request: CreateStrategicGuidanceRequest
    ): StrategicGuidance {
        return RetrofitInstance.api.createGuidance(
            authorization = "Bearer $token",
            request = request
        )
    }

    suspend fun updateGuidance(
        token: String,
        id: String,
        request: CreateStrategicGuidanceRequest
    ): StrategicGuidance {
        return RetrofitInstance.api.updateGuidance(
            authorization = "Bearer $token",
            id = id,
            request = request
        )
    }

    suspend fun deleteGuidance(
        token: String,
        id: String
    ) {
        RetrofitInstance.api.deleteGuidance(
            authorization = "Bearer $token",
            id = id
        )
    }
}