package com.darkstar.client.controller

import com.darkstar.client.exception.BadRequestException
import com.darkstar.client.model.DarkStarResponse
import com.darkstar.client.model.HealthRequest
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
    fun publishTelemetryData(@RequestBody telemetryRequest: TelemetryRequest): DarkStarResponse {
        val hasPublishedTelemetryData = darkStarService.retrieveTelemetryResponse(telemetryRequest)
        val message = "Telemetry data published successfully".takeIf { hasPublishedTelemetryData } ?: "Publishing Telemetry Data Unsuccessful"
        return DarkStarResponse(message = message)
    }

    @PostMapping("health")
    @ResponseStatus(HttpStatus.CREATED)
    fun publishHealthData(@RequestBody healthRequest: HealthRequest): DarkStarResponse {
        val hasPublishedHealthData = darkStarService.retrieveHealthResponse(healthRequest)
        val message = "Health data published successfully".takeIf { hasPublishedHealthData } ?: "Publishing Health Data Unsuccessful"
        return DarkStarResponse(message = message)
    }

    @PostMapping("images")
    @ResponseStatus(HttpStatus.CREATED)
    fun publishImageData(@RequestBody byteArray: ByteArray, @RequestParam(required = false) missionId: Long?): DarkStarResponse {
        if(missionId == null)
            throw BadRequestException("Request should have missionId in the query param")
        val hasPublishedImageData = darkStarService.retrieveImageResponse(byteArray, missionId)
        val message = "Images published successfully".takeIf { hasPublishedImageData } ?: "Publishing Images Unsuccessful"
        return DarkStarResponse(message = message)
    }
}