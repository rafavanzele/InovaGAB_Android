package br.com.fiap.inovagab.data.remote.model

data class UpdateIdeaRequest(
    val titulo: String,
    val descricao: String,
    val categoria: String
)