package edu.ucne.fluentpath.Presentation.screens.ProfesoresScreens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.fluentpath.data.local.entities.ProfesorEntity
import edu.ucne.fluentpath.data.repository.ProfesorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfesorDetailViewModel @Inject constructor(
    private val repository: ProfesorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProfesorDetailState())
    val state: StateFlow<ProfesorDetailState> = _state.asStateFlow()


    private val profesorId: Int = savedStateHandle.get<Int>("profesorId") ?: 0

    init {
        loadProfesor()
    }

    fun loadProfesor() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getProfesorById(profesorId).collect { profesor ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            profesor = profesor,
                            error = if (profesor == null) "Profesor no encontrado" else null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

