package edu.ucne.composedemo.presentation.sistema
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.remote.Resource
import edu.ucne.composedemo.data.remote.dto.SistemaDto
import edu.ucne.composedemo.data.repository.SistemaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SistemaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
    }

    fun validarCampos(): Boolean {
        return !_uiState.value.nombre.isNullOrBlank()
    }

    fun update() {
        viewModelScope.launch {
            sistemaRepository.update(SistemaDto(
                idSistema = _uiState.value.sistemaId?: 0,
                nombre = _uiState.value.nombre?:""
            ))
        }
    }


    fun getSistemas() {
        viewModelScope.launch {
            sistemaRepository.getSistemas().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                sistemas = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(errorMessage = result.message ?: "Error desconocido",
                                isLoading = false)
                        }
                    }
                }
            }
        }
    }
}

data class SistemaUiState(
    val sistemaId: Int? = null,
    val nombre: String? = "",
    val errorMessage: String? = null,
    val sistemas: List<SistemaDto> = emptyList(),
    val isLoading: Boolean = false,
)


fun SistemaUiState.toEntity() = SistemaDto(
    idSistema = sistemaId ?: 0,
    nombre = nombre ?: ""
)