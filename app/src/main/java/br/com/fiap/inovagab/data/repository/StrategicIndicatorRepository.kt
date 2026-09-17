package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.remote.model.StrategicIndicator
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import br.com.fiap.inovagab.data.remote.model.CreateStrategicIndicatorRequest


class StrategicIndicatorRepository {

    suspend fun getIndicators(
        token: String
    ): List<StrategicIndicator> {

        return RetrofitInstance.api.getIndicators(
            authorization = "Bearer $token"
        )
    }

    suspend fun createIndicator(
        token: String,
        request: CreateStrategicIndicatorRequest
    ): StrategicIndicator {

        return RetrofitInstance.api.createIndicator(
            authorization = "Bearer $token",
            request = request
        )
    }

    suspend fun updateIndicator(
        token: String,
        id: String,
        request: CreateStrategicIndicatorRequest
    ): StrategicIndicator {

        return RetrofitInstance.api.updateIndicator(
            authorization = "Bearer $token",
            id = id,
            request = request
        )
    }

    suspend fun deleteIndicator(
        token: String,
        id: String
    ) {
        RetrofitInstance.api.deleteIndicator(
            authorization = "Bearer $token",
            id = id
        )
    }
}