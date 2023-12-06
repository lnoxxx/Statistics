package com.statistics

import com.statistics.routings.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 5052, module = Application::module).start(wait = true)
}

fun Application.module() {
    statisticRouting()
    configureSerialization()
}

