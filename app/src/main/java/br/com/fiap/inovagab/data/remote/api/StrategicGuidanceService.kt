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
import br.com.fiap.inovagab.data.remote.model.CreateStrategicIndicatorRequest

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

    @GET("api/IndicadoresEstrategicos")
    suspend fun getIndicators(
        @Header("Authorization") authorization: String
    ): List<StrategicIndicator>

    @POST("api/IndicadoresEstrategicos")
    suspend fun createIndicator(
        @Header("Authorization") authorization: String,
        @Body request: CreateStrategicIndicatorRequest
    ): StrategicIndicator


    @PUT("api/IndicadoresEstrategicos/{id}")
    suspend fun updateIndicator(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body request: CreateStrategicIndicatorRequest
    ): StrategicIndicator


    @DELETE("api/IndicadoresEstrategicos/{id}")
    suspend fun deleteIndicator(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    )

    @GET("achievedResults")
    suspend fun getAchievedResults(): List<AchievedResult>
}