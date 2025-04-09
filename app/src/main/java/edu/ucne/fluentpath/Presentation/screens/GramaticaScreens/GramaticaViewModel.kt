package edu.ucne.fluentpath.Presentation.screens.GramaticaScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.fluentpath.data.local.entities.GramaticaEntity
import edu.ucne.fluentpath.data.repository.GramaticaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GramaticaViewModel @Inject constructor(
    private val repository: GramaticaRepository
) : ViewModel() {
    private val _state = MutableStateFlow(GramaticaState())
    val state: StateFlow<GramaticaState> = _state.asStateFlow()

    init {
        loadGramaticas()
        syncData()
    }

    fun loadGramaticas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getAllGramaticas().collect { gramaticas ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            gramaticas = gramaticas,
                            error = if (gramaticas.isEmpty()) "No hay gramáticas" else null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun syncData() {
        viewModelScope.launch {
            try {
                repository.syncGramaticas()
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error de sincronización: ${e.message}") }
            }
        }
    }
}
