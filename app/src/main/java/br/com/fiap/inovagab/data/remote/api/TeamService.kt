package br.com.fiap.inovagab.data.remote.api

import br.com.fiap.inovagab.data.remote.model.CreateTeamRequest
import br.com.fiap.inovagab.data.remote.model.TeamResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TeamService {

    @GET("api/Equipes")
    suspend fun getTeams(
        @Header("Authorization") authorization: String
    ): List<TeamResponse>

    @GET("api/Equipes/{id}")
    suspend fun getTeamById(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): TeamResponse

    @POST("api/Equipes")
    suspend fun createTeam(
        @Header("Authorization") authorization: String,
        @Body request: CreateTeamRequest
    ): TeamResponse

    @PUT("api/Equipes/{id}")
    suspend fun updateTeam(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body request: CreateTeamRequest
    ): TeamResponse

    @DELETE("api/Equipes/{id}")
    suspend fun deleteTeam(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    )
}