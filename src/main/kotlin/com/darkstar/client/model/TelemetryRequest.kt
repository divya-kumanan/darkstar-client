package com.darkstar.client.model

import java.time.LocalDateTime

data class TelemetryRequest(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val velocity: Double,
    val systemStatus: TelemetryStatus,
    val timestamp: LocalDateTime,
    val mission: Mission
)

enum class TelemetryStatus {
    RED, GREEN, BLUE
}