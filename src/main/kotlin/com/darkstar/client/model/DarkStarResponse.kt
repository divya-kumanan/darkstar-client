package com.darkstar.client.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DarkStarResponse(
    val message: String? = null,
    val code: Number? = null
)