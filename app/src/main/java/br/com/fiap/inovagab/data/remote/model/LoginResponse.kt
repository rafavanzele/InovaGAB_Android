package br.com.fiap.inovagab.data.remote.model

data class LoginResponse(
    val id: String,
    val nome: String,
    val email: String,
    val perfil: String,
    val token: String
)