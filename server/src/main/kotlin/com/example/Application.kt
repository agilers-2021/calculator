package com.example

import com.example.models.ExpressionResultModel
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  val connection = Database.connect(
    "jdbc:h2:mem:test",
    driver = "org.h2.Driver"
  )

  transaction(connection) {
    EvaluationStorage.init()
  }

  embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
    install(ContentNegotiation) {
      json()
    }
    configureRouting()
  }.start(wait = true)
}
