package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.remote.model.StrategicIndicator
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance

class StrategicIndicatorRepository {

    suspend fun getIndicators(): List<StrategicIndicator> {
        return RetrofitInstance.api.getIndicators()
    }
}