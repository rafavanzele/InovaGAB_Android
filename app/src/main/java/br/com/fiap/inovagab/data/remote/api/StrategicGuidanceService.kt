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
import retrofit2.http.Header
import br.com.fiap.inovagab.data.remote.model.CreateStrategicGuidanceRequest

interface StrategicGuidanceService {

    @GET("orientacoes")
    suspend fun getGuidances(): List<StrategicGuidance>

    @GET("api/DiretrizesEstrategicas")
    suspend fun getStrategicGuidances(
        @Header("Authorization") authorization: String
    ): List<StrategicGuidance>

    @POST("api/DiretrizesEstrategicas")
    suspend fun createGuidance(
        @Header("Authorization") authorization: String,
        @Body request: CreateStrategicGuidanceRequest
    ): StrategicGuidance

    @PUT("api/DiretrizesEstrategicas/{id}")
    suspend fun updateGuidance(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body request: CreateStrategicGuidanceRequest
    ): StrategicGuidance

    @DELETE("api/DiretrizesEstrategicas/{id}")
    suspend fun deleteGuidance(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    )

    @GET("strategicIndicators")
    suspend fun getIndicators(): List<StrategicIndicator>

    @GET("achievedResults")
    suspend fun getAchievedResults(): List<AchievedResult>
}