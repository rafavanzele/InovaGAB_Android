package br.com.fiap.inovagab.data.remote.api

import br.com.fiap.inovagab.data.remote.model.CreateIdeaRequest
import br.com.fiap.inovagab.data.remote.model.IdeaResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import br.com.fiap.inovagab.data.remote.model.UpdateIdeaRequest
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface IdeaService {

    @GET("api/Ideias")
    suspend fun getMyIdeas(
        @Header("Authorization") authorization: String
    ): List<IdeaResponse>

    @POST("api/Ideias")
    suspend fun createIdea(
        @Header("Authorization") authorization: String,
        @Body request: CreateIdeaRequest
    ): IdeaResponse

    @PUT("api/Ideias/{id}")
    suspend fun updateIdea(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
        @Body request: UpdateIdeaRequest
    ): IdeaResponse

    @DELETE("api/Ideias/{id}")
    suspend fun deleteIdea(
        @Path("id") id: String,
        @Header("Authorization") authorization: String
    ): Response<Unit>
}