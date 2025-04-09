package edu.ucne.fluentpath.Presentation.screens.ProfesoresScreens

import edu.ucne.fluentpath.data.local.entities.ProfesorEntity

data class ProfesorListState(
    val isLoading: Boolean = false,
    val profesores: List<ProfesorEntity> = emptyList(),
    val error: String? = null
)