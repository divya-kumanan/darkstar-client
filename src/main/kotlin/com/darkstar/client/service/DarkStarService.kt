package com.darkstar.client.service

import com.darkstar.client.config.DarkStarServerConfig
import com.darkstar.client.exception.DarkStarServerException
import com.darkstar.client.exception.MissionNotFoundException
import com.darkstar.client.model.HealthRequest
import com.darkstar.client.model.TelemetryRequest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class DarkStarService(private val restTemplate: RestTemplate, private val serverConfig: DarkStarServerConfig) {
    fun retrieveTelemetryResponse(request: TelemetryRequest): Boolean {
        try {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val entity = HttpEntity(request, headers)
            val uri = UriComponentsBuilder.fromUriString(serverConfig.host + serverConfig.telemetryUrl).build().toUri()

            val response = restTemplate.postForEntity(uri, entity, String::class.java)

            return response.statusCode.is2xxSuccessful
        } catch (ex: HttpStatusCodeException) {
            val responseBodyAsString = ex.responseBodyAsString
            throw DarkStarServerException("DarkStar Server API returned unsuccessful response : + $responseBodyAsString")
        } catch (ex: RestClientException) {
            throw DarkStarServerException("Error calling DarkStar Server API")
        } catch (e: Exception) {
            throw DarkStarServerException("Error calling DarkStar Server API")
        }
    }

    fun retrieveHealthResponse(request: HealthRequest): Boolean {
        try {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val entity = HttpEntity(request, headers)
            val uri = UriComponentsBuilder.fromUriString(serverConfig.host + serverConfig.healthUrl).build().toUri()

            val response = restTemplate.postForEntity(uri, entity, String::class.java)

            return response.statusCode.is2xxSuccessful
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND)
                throw MissionNotFoundException("Mission does not exists")
            val responseBodyAsString = ex.responseBodyAsString
            throw DarkStarServerException("DarkStar Server API returned unsuccessful response : + $responseBodyAsString")
        } catch (ex: RestClientException) {
            throw DarkStarServerException("Error calling DarkStar Server API")
        } catch (e: Exception) {
            throw DarkStarServerException("Error calling DarkStar Server API")
        }
    }

    fun retrieveImageResponse(byteArray: ByteArray, @RequestParam(required = false) missionId: Long?): Boolean {
        try {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_OCTET_STREAM

            val entity = HttpEntity(byteArray, headers)
            val uri = UriComponentsBuilder
                .fromUriString(serverConfig.host + serverConfig.imageUrl)
                .queryParam("missionId", missionId)
                .build()
                .toUri()

            val response = restTemplate.postForEntity(uri, entity, String::class.java)

            return response.statusCode.is2xxSuccessful
        } catch (ex: HttpStatusCodeException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND)
                throw MissionNotFoundException("Mission does not exists")
            val responseBodyAsString = ex.responseBodyAsString
            throw DarkStarServerException("DarkStar Server API returned unsuccessful response : + $responseBodyAsString")
        } catch (ex: RestClientException) {
            throw DarkStarServerException("Error calling DarkStar Server API")
        } catch (e: Exception) {
            throw DarkStarServerException("Error calling DarkStar Server API")
        }
    }
}