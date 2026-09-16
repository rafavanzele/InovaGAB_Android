package br.com.fiap.inovagab.data.remote.model

data class CreateIdeaRequest(
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val diretrizId: String
)