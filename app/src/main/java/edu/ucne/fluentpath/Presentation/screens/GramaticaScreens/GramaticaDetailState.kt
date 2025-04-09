package edu.ucne.fluentpath.Presentation.screens.GramaticaScreens

import edu.ucne.fluentpath.data.local.entities.GramaticaEntity

data class GramaticaDetailState(
    val gramatica: GramaticaEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)