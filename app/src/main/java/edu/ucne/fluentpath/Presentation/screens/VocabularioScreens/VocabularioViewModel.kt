package edu.ucne.fluentpath.Presentation.screens.VocabularioScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.fluentpath.data.local.entities.VocabularioEntity
import edu.ucne.fluentpath.data.repository.VocabularioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularioViewModel @Inject constructor(
    private val repository: VocabularioRepository
) : ViewModel() {
    private val _state = MutableStateFlow(VocabularioState())
    val state: StateFlow<VocabularioState> = _state.asStateFlow()

    init {
        loadInitialData()
    }

    fun handleIntent(intent: VocabularioIntent) {
        when (intent) {
            VocabularioIntent.LoadVocabularios -> loadInitialData()
            is VocabularioIntent.VocabularioSelected -> selectVocabulario(intent.id)
            VocabularioIntent.Refresh -> refreshData()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)

                repository.getAllVocabularios().collect { localData ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        vocabularios = localData,
                        error = if (localData.isEmpty()) "No hay datos locales" else null
                    )
                }

                launch {
                    try {
                        repository.syncVocabularios()
                    } catch (e: Exception) {
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al cargar: ${e.message ?: "Error desconocido"}"
                )
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                repository.syncVocabularios()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al actualizar: ${e.message ?: "Error desconocido"}"
                )
            }
        }
    }

    private fun selectVocabulario(id: Int) {
        _state.value = _state.value.copy(selectedVocabularioId = id)
    }
}


sealed class VocabularioIntent {
    object LoadVocabularios : VocabularioIntent()
    data class VocabularioSelected(val id: Int) : VocabularioIntent()
    object Refresh : VocabularioIntent()
}



