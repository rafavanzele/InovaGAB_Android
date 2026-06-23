package br.com.fiap.inovagab.data.model

data class Project(
    val id: Int,
    val title: String,
    val description: String,
    val responsible: String,
    val status: String,
    val deadline: String,
    val investment: String,
    val expectedReturn: String,
    val result: String,
    val progress: Float
)