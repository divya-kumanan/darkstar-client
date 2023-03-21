package com.darkstar.client.exception

class DarkStarServerException(message: String): RuntimeException(message)

class MissionNotFoundException(message: String) : RuntimeException(message)

class BadRequestException(message: String): RuntimeException(message)
