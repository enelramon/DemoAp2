package edu.ucne.composedemo.presentation.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.data.repository.FeatureFlagRepository
import javax.inject.Inject

@HiltViewModel
class FeatureFlagViewModel @Inject constructor(
    private val repository: FeatureFlagRepository
) : ViewModel() {
    suspend fun isTicketEnabled(): Boolean = repository.isTicketEnabled()
}