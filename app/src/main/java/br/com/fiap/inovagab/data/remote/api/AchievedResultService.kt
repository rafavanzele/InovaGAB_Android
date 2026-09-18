package br.com.fiap.inovagab.data.remote.api

import br.com.fiap.inovagab.data.remote.model.AchievedResultResponse
import br.com.fiap.inovagab.data.remote.model.CreateAchievedResultRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AchievedResultService {

    @GET("api/ResultadosAlcancados")
    suspend fun getAchievedResults(
        @Header("Authorization") authorization: String
    ): List<AchievedResultResponse>

    @POST("api/ResultadosAlcancados")
    suspend fun createAchievedResult(
        @Header("Authorization") authorization: String,
        @Body request: CreateAchievedResultRequest
    ): AchievedResultResponse

    @PUT("api/ResultadosAlcancados/{id}")
    suspend fun updateAchievedResult(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body request: CreateAchievedResultRequest
    ): AchievedResultResponse

    @DELETE("api/ResultadosAlcancados/{id}")
    suspend fun deleteAchievedResult(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    )
}