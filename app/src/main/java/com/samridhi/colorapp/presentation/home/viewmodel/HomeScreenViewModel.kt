package com.samridhi.colorapp.presentation.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samridhi.colorapp.data.local.db.entity.ColorEntity
import com.samridhi.colorapp.data.network.repository.ColorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ColorRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeScreenUiState())
        private set

    init {
        fetchColorsFromRoom()
    }

    private fun fetchColorsFromRoom() {
        viewModelScope.launch {
            val colors = repository.getAllColors()
            val totalCount = repository.getTotalCount()
            val unSyncedCount = repository.getUnsyncedCount()

            uiState = if (colors.isEmpty()) {
                uiState.copy(screenState = ScreenState.EMPTY)
            } else {
                uiState.copy(
                    screenState = ScreenState.DEFAULT,
                    list = colors,
                    totalCount = totalCount,
                    unSyncedCount = unSyncedCount
                )
            }
        }
    }

    fun onEvent(event: HomeScreenUIEvent) {
        when (event) {
            HomeScreenUIEvent.OnSync -> syncColors()
            HomeScreenUIEvent.OnAddColor -> addColor()
        }
    }

    private fun syncColors() {
        viewModelScope.launch {
            try {
                repository.syncColors(
                    onSuccess = {
                        uiState = uiState.copy(unSyncedCount = 0)
                    }
                )
                fetchColorsFromRoom()
            } catch (e: Exception) {
                uiState = uiState.copy(screenState = ScreenState.ERROR)
            }
        }
    }

    private fun addColor() {
        viewModelScope.launch {
            repository.addRandomColor()
            fetchColorsFromRoom()
        }
    }
}

data class HomeScreenUiState(
    val screenState: ScreenState = ScreenState.LOADING,
    val list: List<ColorEntity> = emptyList(),
    val totalCount: Int = 0,
    val unSyncedCount: Int = 0
)

sealed class HomeScreenUIEvent {
    data object OnSync : HomeScreenUIEvent()
    data object OnAddColor : HomeScreenUIEvent()
}

enum class ScreenState {
    LOADING,
    EMPTY,
    ERROR,
    DEFAULT
}
