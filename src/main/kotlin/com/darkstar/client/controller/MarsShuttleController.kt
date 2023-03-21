package com.darkstar.client.controller

import com.darkstar.client.exception.BadRequestException
import com.darkstar.client.model.DarkStarResponse
import com.darkstar.client.model.HealthRequest
import com.darkstar.client.model.TelemetryRequest
import com.darkstar.client.service.DarkStarService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mars-shuttle")
@Tag(name = "DarkStar MarsShuttle API", description = "MarsShuttle API Documentation")
class MarsShuttleController(private val darkStarService: DarkStarService) {
    @Operation(summary = "Publish Telemetry Data")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Publishing Telemetry Data Successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = DarkStarResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "400", description = "Invalid Request",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = DarkStarResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "404", description = "Mission does not exists",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = DarkStarResponse::class))
                    )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = DarkStarResponse::class))
                    )]
            )
        ]
    )
    @PostMapping("telemetry")
    @ResponseStatus(HttpStatus.OK)
    fun publishTelemetryData(@RequestBody telemetryRequest: TelemetryRequest): DarkStarResponse {
        val hasPublishedTelemetryData = darkStarService.retrieveTelemetryResponse(telemetryRequest)
        val message = "Telemetry data published successfully".takeIf { hasPublishedTelemetryData } ?: "Publishing Telemetry Data Unsuccessful"
        return DarkStarResponse(message = message)
    }

    @PostMapping("health")
    @ResponseStatus(HttpStatus.OK)
    fun publishHealthData(@RequestBody healthRequest: HealthRequest): DarkStarResponse {
        val hasPublishedHealthData = darkStarService.retrieveHealthResponse(healthRequest)
        val message = "Health data published successfully".takeIf { hasPublishedHealthData } ?: "Publishing Health Data Unsuccessful"
        return DarkStarResponse(message = message)
    }

    @PostMapping("images")
    @ResponseStatus(HttpStatus.OK)
    fun publishImageData(@RequestBody byteArray: ByteArray, @RequestParam(required = false) missionId: Long?): DarkStarResponse {
        if(missionId == null)
            throw BadRequestException("Request should have missionId in the query param")
        val hasPublishedImageData = darkStarService.retrieveImageResponse(byteArray, missionId)
        val message = "Images published successfully".takeIf { hasPublishedImageData } ?: "Publishing Images Unsuccessful"
        return DarkStarResponse(message = message)
    }
}