package edu.ucne.fluentpath.Presentation.screens.ProfesoresScreens

import edu.ucne.fluentpath.data.local.entities.ProfesorEntity

data class ProfesorDetailState(
    val profesor: ProfesorEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)