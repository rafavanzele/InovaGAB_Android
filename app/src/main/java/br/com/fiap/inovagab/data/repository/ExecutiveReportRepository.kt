package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.remote.model.ExecutiveReportResponse
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance

class ExecutiveReportRepository {

    suspend fun getExecutiveReport(
        token: String
    ): ExecutiveReportResponse {

        return RetrofitInstance.api.getExecutiveReport(
            authorization = "Bearer $token"
        )
    }
}