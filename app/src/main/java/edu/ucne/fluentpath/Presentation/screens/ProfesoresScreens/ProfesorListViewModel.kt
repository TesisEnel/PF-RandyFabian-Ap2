package edu.ucne.fluentpath.Presentation.screens.ProfesoresScreens

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
class ProfesorListViewModel @Inject constructor(
    private val repository: ProfesorRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfesorListState())
    val state: StateFlow<ProfesorListState> = _state.asStateFlow()

    init {
        loadInitialData()
        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(30_000)
                try {
                    repository.syncProfesores()
                } catch (e: Exception) {
                }
            }
        }
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getAllProfesores().collect { profesores ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            profesores = profesores,
                            error = if (profesores.isEmpty()) "No hay datos locales" else null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error: ${e.message ?: "Error desconocido"}"
                    )
                }
            }
        }
    }
}

