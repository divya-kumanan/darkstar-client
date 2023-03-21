package com.darkstar.client.service

import com.darkstar.client.config.DarkStarServerConfig
import com.darkstar.client.exception.DarkStarServerException
import com.darkstar.client.model.*
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.*
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
class DarkStarServiceTest {

    @Mock
    private lateinit var restTemplate: RestTemplate

    @Mock
    private lateinit var darkStarApiConfig: DarkStarServerConfig

    @InjectMocks
    private lateinit var darkStarService: DarkStarService

    @BeforeEach
    fun setUp() {
        `when`(darkStarApiConfig.host).thenReturn("https://req.com")
    }

    @Test
    fun `when telemetry api return successful response`() {
        //given
        val dateString = "2023-03-21T10:15:30"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val request = TelemetryRequest(
            latitude = 18.56,
            longitude = 10.56,
            altitude = 12.4,
            velocity = 97.8,
            TelemetryStatus.DEGRADED,
            timestamp = dateTime,
            mission = Mission(1L)
        )
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(request, headers)
        val uri = UriComponentsBuilder.fromUriString("https://req.com/api/mars-shuttle/telemetry").build().toUri()

        `when`(darkStarApiConfig.telemetryUrl).thenReturn("/api/mars-shuttle/telemetry")
        `when`(
            restTemplate.postForEntity(
                uri,
                entity,
                String::class.java
            )
        ).thenReturn(ResponseEntity(HttpStatus.CREATED))

        //when
        val response = darkStarService.retrieveTelemetryResponse(request)

        //then
        assertTrue(response)
    }

    @Test
    fun `when telemetry api returns exception`() {
        //given
        val dateString = "2023-03-21T10:15:30"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val request = TelemetryRequest(
            latitude = 18.56,
            longitude = 10.56,
            altitude = 12.4,
            velocity = 97.8,
            TelemetryStatus.DEGRADED,
            timestamp = dateTime,
            mission = Mission(1L)
        )
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(request, headers)
        val uri = UriComponentsBuilder.fromUriString("https://req.com/api/mars-shuttle/telemetry").build().toUri()

        `when`(darkStarApiConfig.telemetryUrl).thenReturn("/api/mars-shuttle/telemetry")
        `when`(
            restTemplate.postForEntity(
                uri,
                entity,
                String::class.java
            )
        ).thenThrow(RestClientException("RestClientException"))

        // when, then
        val exception = assertThrows(DarkStarServerException::class.java) {
            darkStarService.retrieveTelemetryResponse(request)
        }

        assertEquals("Error calling DarkStar Server API", exception.message)
    }

    @Test
    fun `when health api return successful response`() {
        //given
        val dateString = "2023-03-21T10:15:30"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val request = HealthRequest(
            reportingSystem = ReportingSystem.COMMUNICATION,
            status = HealthStatus.DOWN,
            mission = Mission(1L),
            reportingTimestamp = dateTime
        )
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(request, headers)
        val uri = UriComponentsBuilder.fromUriString("https://req.com/api/mars-shuttle/health").build().toUri()

        `when`(darkStarApiConfig.healthUrl).thenReturn("/api/mars-shuttle/health")
        `when`(
            restTemplate.postForEntity(
                uri,
                entity,
                String::class.java
            )
        ).thenReturn(ResponseEntity(HttpStatus.CREATED))

        //when
        val response = darkStarService.retrieveHealthResponse(request)

        //then
        assertTrue(response)
    }

    @Test
    fun `when health api return exception`() {
        //given
        val dateString = "2023-03-21T10:15:30"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val request = HealthRequest(
            reportingSystem = ReportingSystem.COMMUNICATION,
            status = HealthStatus.DOWN,
            mission = Mission(1L),
            reportingTimestamp = dateTime
        )
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(request, headers)
        val uri = UriComponentsBuilder.fromUriString("https://req.com/api/mars-shuttle/health").build().toUri()

        `when`(darkStarApiConfig.healthUrl).thenReturn("/api/mars-shuttle/health")
        `when`(
            restTemplate.postForEntity(
                uri,
                entity,
                String::class.java
            )
        ).thenThrow(RestClientException("RestClientException"))

        // when, then
        val exception = assertThrows(DarkStarServerException::class.java) {
            darkStarService.retrieveHealthResponse(request)
        }

        assertEquals("Error calling DarkStar Server API", exception.message)
    }

    @Test
    fun `when image api return successful response`() {
        //given
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        val entity = HttpEntity(byteArrayOf(0x12, 0x34, 0x56, 0x78), headers)
        val uri = UriComponentsBuilder
            .fromUriString("https://req.com/api/mars-shuttle/images")
            .queryParam("missionId", 1L)
            .build().toUri()


        `when`(darkStarApiConfig.imageUrl).thenReturn("/api/mars-shuttle/images")
        `when`(
            restTemplate.postForEntity(
                uri,
                entity,
                String::class.java
            )
        ).thenReturn(ResponseEntity(HttpStatus.CREATED))

        //when
        val response = darkStarService.retrieveImageResponse(byteArrayOf(0x12, 0x34, 0x56, 0x78), 1L)

        //then
        assertTrue(response)
    }

    @Test
    fun `when image api return exception`() {
        //given
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        val entity = HttpEntity(byteArrayOf(0x12, 0x34, 0x56, 0x78), headers)
        val uri = UriComponentsBuilder
            .fromUriString("https://req.com/api/mars-shuttle/images")
            .queryParam("missionId", 1L)
            .build().toUri()


        `when`(darkStarApiConfig.imageUrl).thenReturn("/api/mars-shuttle/images")
        `when`(
            restTemplate.postForEntity(
                uri,
                entity,
                String::class.java
            )
        ).thenThrow(RestClientException("RestClientException"))

        // when, then
        val exception = assertThrows(DarkStarServerException::class.java) {
            darkStarService.retrieveImageResponse(byteArrayOf(0x12, 0x34, 0x56, 0x78), 1L)
        }

        assertEquals("Error calling DarkStar Server API", exception.message)
    }
}