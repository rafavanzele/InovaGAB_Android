package br.com.fiap.inovagab.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.inovagab.data.model.Idea
import br.com.fiap.inovagab.data.model.IdeaStatus
import br.com.fiap.inovagab.data.repository.IdeaRepository

class IdeaViewModel : ViewModel() {

    private val repository = IdeaRepository()

    val ideas = repository.ideas

    fun addIdea(idea: Idea) {
        repository.addIdea(idea)
    }

    fun updateIdeaStatus(
        ideaId: Int,
        newStatus: IdeaStatus
    ) {
        repository.updateIdeaStatus(
            ideaId = ideaId,
            newStatus = newStatus
        )
    }
}