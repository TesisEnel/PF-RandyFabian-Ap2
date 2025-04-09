package edu.ucne.fluentpath.Presentation.screens.GramaticaScreens

import edu.ucne.fluentpath.data.local.entities.GramaticaEntity

data class GramaticaState(
    val isLoading: Boolean = false,
    val gramaticas: List<GramaticaEntity> = emptyList(),
    val error: String? = null
)