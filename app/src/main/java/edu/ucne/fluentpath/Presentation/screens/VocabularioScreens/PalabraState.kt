package edu.ucne.fluentpath.Presentation.screens.VocabularioScreens

import edu.ucne.fluentpath.data.remote.dto.PalabraDto

data class PalabraState(
    val isLoading: Boolean = false,
    val palabras: List<PalabraDto> = emptyList(),
    val vocabularioId: Int? = null,
    val error: String? = null
)