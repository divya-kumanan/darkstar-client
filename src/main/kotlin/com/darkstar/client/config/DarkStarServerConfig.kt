package com.darkstar.client.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class DarkStarServerConfig(@Value("\${dark-star.server.api.host}") val host: String,
                           @Value("\${dark-star.server.api.telemetryUrl}") val telemetryUrl: String,
                           @Value("\${dark-star.server.api.healthUrl}") val healthUrl: String,
                           @Value("\${dark-star.server.api.imageUrl}") val imageUrl: String
)