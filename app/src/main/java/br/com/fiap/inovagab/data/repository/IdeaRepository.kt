package br.com.fiap.inovagab.data.repository

import br.com.fiap.inovagab.data.model.Idea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import br.com.fiap.inovagab.data.model.IdeaStatus

class IdeaRepository {

    private val _ideas = MutableStateFlow<List<Idea>>(emptyList())

    val ideas: StateFlow<List<Idea>> = _ideas

    fun addIdea(idea: Idea) {
        _ideas.value = _ideas.value + idea
    }

    fun updateIdeaStatus(
        ideaId: Int,
        newStatus: IdeaStatus
    ) {
        _ideas.value = _ideas.value.map { idea ->

            if (idea.id == ideaId) {
                idea.copy(status = newStatus)
            } else {
                idea
            }
        }
    }
}