package br.com.fiap.inovagab.data.remote.model

data class IdeaResponse(
    val id: String,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val diretrizId: String,
    val autorId: String,
    val autorNome: String,
    val status: String,
    val priorizada: Boolean,
    val dataCriacao: String
)