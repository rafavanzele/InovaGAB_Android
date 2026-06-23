package br.com.fiap.inovagab.data.remote.api

import br.com.fiap.inovagab.data.model.AchievedResult
import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import br.com.fiap.inovagab.data.remote.model.StrategicIndicator
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StrategicGuidanceService {

    @GET("orientacoes")
    suspend fun getGuidances(): List<StrategicGuidance>

    @POST("orientacoes")
    suspend fun createGuidance(
        @Body strategicGuidance: StrategicGuidance
    ): StrategicGuidance

    @PUT("orientacoes/{id}")
    suspend fun updateGuidance(
        @Path("id") id: String,
        @Body strategicGuidance: StrategicGuidance
    ): StrategicGuidance

    @DELETE("orientacoes/{id}")
    suspend fun deleteGuidance(
        @Path("id") id: String
    )

    @GET("strategicIndicators")
    suspend fun getIndicators(): List<StrategicIndicator>

    @GET("achievedResults")
    suspend fun getAchievedResults(): List<AchievedResult>
}