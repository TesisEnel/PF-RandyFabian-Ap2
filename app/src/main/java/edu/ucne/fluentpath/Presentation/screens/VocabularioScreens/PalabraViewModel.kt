
package edu.ucne.fluentpath.Presentation.screens.PalabraScreens

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.fluentpath.Presentation.screens.VocabularioScreens.PalabraState
import edu.ucne.fluentpath.data.repository.PalabraRepository
import edu.ucne.fluentpath.data.remote.dto.PalabraDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PalabraViewModel @Inject constructor(
    private val repository: PalabraRepository,
    private val context: Context
) : ViewModel(), TextToSpeech.OnInitListener {
    private val _state = MutableStateFlow(PalabraState())
    val state: StateFlow<PalabraState> = _state.asStateFlow()
    private var textToSpeech: TextToSpeech? = null
    private var isEnglishSupported = false

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.US)
            when (result) {
                TextToSpeech.LANG_MISSING_DATA -> {
                    Log.e("TTS", "English language data missing")
                    val installIntent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
                    context.startActivity(installIntent)
                }
                TextToSpeech.LANG_NOT_SUPPORTED -> Log.e("TTS", "English not supported")
                else -> isEnglishSupported = true
            }
        } else {
            Log.e("TTS", "TTS initialization failed")
        }
    }

    fun handleIntent(intent: PalabraIntent) {
        when (intent) {
            is PalabraIntent.LoadPalabras -> loadPalabras(intent.vocabularioId)
            PalabraIntent.Refresh -> refreshData()
            else -> {}
        }
    }

    private fun loadPalabras(vocabularioId: Int) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null, vocabularioId = vocabularioId) }
                repository.getPalabrasByVocabulario(vocabularioId).collect { palabras ->
                    _state.update { it.copy(
                        isLoading = false,
                        palabras = palabras,
                        error = if (palabras.isEmpty()) "No words found" else null
                    ) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Error: ${e.message}") }
            }
        }
    }

    private fun refreshData() {
        state.value.vocabularioId?.let { vocabularioId ->
            viewModelScope.launch {
                try {
                    _state.update { it.copy(isLoading = true, error = null) }
                    val palabras = repository.refreshPalabras(vocabularioId)
                    _state.update { it.copy(
                        isLoading = false,
                        palabras = palabras,
                        error = if (palabras.isEmpty()) "No words found" else null
                    ) }
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = "Error: ${e.message}") }
                }
            }
        }
    }

    fun speakWordAndDefinition(word: String, definition: String) {
        if (isEnglishSupported) {
            textToSpeech?.apply {
                setLanguage(Locale.US)
                setPitch(1.0f)
                setSpeechRate(0.9f)
                speak(word, TextToSpeech.QUEUE_FLUSH, null, "word_tts")
                speak(definition, TextToSpeech.QUEUE_ADD, null, "definition_tts")
            }
        }
    }

    override fun onCleared() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        super.onCleared()
    }
}

sealed class PalabraIntent {
    data class LoadPalabras(val vocabularioId: Int) : PalabraIntent()
    object Refresh : PalabraIntent()
}