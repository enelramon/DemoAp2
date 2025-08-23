package edu.ucne.composedemo.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureFlagRepository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    suspend fun isTicketEnabled(): Boolean {
        // Fetch and activate to ensure we have the latest values
        try {
            remoteConfig.fetchAndActivate().await()
        } catch (_: Exception) {
            // Ignore fetch errors and fall back to cached/defaults
        }
        return remoteConfig.getBoolean("drawer_ticket_enabled")
    }
}