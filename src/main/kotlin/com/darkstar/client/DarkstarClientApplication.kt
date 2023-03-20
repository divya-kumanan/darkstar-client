package com.darkstar.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DarkstarClientApplication

fun main(args: Array<String>) {
	runApplication<DarkstarClientApplication>(*args)
}