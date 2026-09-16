package br.com.fiap.inovagab.data.remote.api

import br.com.fiap.inovagab.data.remote.model.CreateProjectRequest
import br.com.fiap.inovagab.data.remote.model.ProjectResponse
import br.com.fiap.inovagab.data.remote.model.UpdateProjectRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectService {

    @GET("api/Projetos")
    suspend fun getProjects(
        @Header("Authorization") authorization: String
    ): List<ProjectResponse>

    @GET("api/Projetos/{id}")
    suspend fun getProjectById(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): ProjectResponse

    @POST("api/Projetos")
    suspend fun createProject(
        @Header("Authorization") authorization: String,
        @Body request: CreateProjectRequest
    ): ProjectResponse

    @PUT("api/Projetos/{id}")
    suspend fun updateProject(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body request: UpdateProjectRequest
    ): ProjectResponse

    @DELETE("api/Projetos/{id}")
    suspend fun deleteProject(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    )
}