package br.com.fiap.inovagab.data.model

data class Idea(
    val id: Int,
    val title: String,
    val description: String,
    val authorProfile: String = "Operador",
    val status: IdeaStatus = IdeaStatus.PENDING
)

enum class IdeaStatus {
    PENDING,
    APPROVED,
    REJECTED
}