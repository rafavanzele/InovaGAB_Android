package br.com.fiap.inovagab.data.remote.api

import br.com.fiap.inovagab.data.remote.model.LoginRequest
import br.com.fiap.inovagab.data.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/Usuarios/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}