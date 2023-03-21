package com.darkstar.client.model

import java.time.LocalDateTime

data class HealthRequest(
    val reportingSystem: ReportingSystem,
    val status: HealthStatus,
    val errorCode: String?,
    val errorDescription: String?,
    val reportingTimestamp: LocalDateTime,
    val mission: Mission
)
enum class ReportingSystem{
    ELECTRICAL,
    LIFE_SUPPORT,
    PROPULSION,
    COMMUNICATION
}

enum class HealthStatus{
    UP,
    DOWN,
    UNKNOWN,
    OUT_OF_SERVICE
}