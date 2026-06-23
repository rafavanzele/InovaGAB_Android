package br.com.fiap.inovagab.data.model

data class ContributorRanking(
    val id: Int,
    val nome: String,
    val cargo: String,
    val ideiasEnviadas: Int,
    val ideiasAprovadas: Int,
    val badge: String
)