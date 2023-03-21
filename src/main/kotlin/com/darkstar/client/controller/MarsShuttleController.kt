package com.darkstar.client.controller

import com.darkstar.client.model.DarkStarResponse
import com.darkstar.client.model.TelemetryRequest
import com.darkstar.client.service.DarkStarService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mars-shuttle")
@Tag(name = "DarkStar MarsShuttle API", description = "MarsShuttle API Documentation")
class MarsShuttleController(private val darkStarService: DarkStarService) {
    @PostMapping("telemetry")
    @ResponseStatus(HttpStatus.CREATED)
    fun publishTelemetry(@RequestBody telemetryRequest: TelemetryRequest): DarkStarResponse {
        val hasPublishedTelemetryData = darkStarService.retrieveTelemetryResponse(telemetryRequest)
        val message = "Telemetry data published successfully".takeIf { hasPublishedTelemetryData } ?: "Publishing Telemetry Data Unsuccessful"
        return DarkStarResponse(message = message)
    }
}