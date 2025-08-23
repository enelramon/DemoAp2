package edu.ucne.composedemo.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

enum class FeatureFlag(val key: String, val default: Boolean) {
    DrawerTicket("drawer_ticket_enabled", true),
    // Add future flags here following the same pattern
}

@Singleton
class FeatureFlagRepository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    suspend fun isEnabled(flag: FeatureFlag): Boolean {
        try {
            remoteConfig.fetchAndActivate().await()
        } catch (_: Exception) {
        }
        return remoteConfig.getBoolean(flag.key)
    }
}