package edu.ucne.fluentpath.Presentation.screens.VocabularioScreens

import edu.ucne.fluentpath.data.local.entities.VocabularioEntity

data class VocabularioState(
    val isLoading: Boolean = false,
    val vocabularios: List<VocabularioEntity> = emptyList(),
    val selectedVocabularioId: Int? = null,
    val error: String? = null
)