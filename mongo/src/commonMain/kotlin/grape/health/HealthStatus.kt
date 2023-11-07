package grape.health

import kotlinx.serialization.Serializable

@Serializable
data class HealthStatus(
    val status: String,
    val millis: String
)