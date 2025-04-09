package edu.ucne.fluentpath.Presentation.screens.GramaticaScreens

import androidx.lifecycle.SavedStateHandle
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
class GramaticaDetailViewModel @Inject constructor(
    private val repository: GramaticaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(GramaticaDetailState())
    val state: StateFlow<GramaticaDetailState> = _state.asStateFlow()

    private val gramaticaId: Int = savedStateHandle.get<Int>("gramaticaId") ?: 0

    init {
        loadGramatica()
    }

    fun loadGramatica() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getGramaticaById(gramaticaId).collect { gramatica ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            gramatica = gramatica,
                            error = if (gramatica == null) "Gramática no encontrada" else null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

