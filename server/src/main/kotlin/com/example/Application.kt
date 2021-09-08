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
    Master.connection = Database.connect(
        "jdbc:h2:./testdb",
        driver = "org.h2.Driver"
    )

    transaction {
        EvaluationStorage.init()
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        configureRouting()
    }.start(wait = true)
}

object Master {

    lateinit var connection: Database

    fun put(expr: ExpressionResultModel) =
        transaction(connection) {
            EvaluationStorage.put(expr)
        }

    fun takeLast(n: Int): List<ExpressionResultModel> =
        transaction(connection) {
            EvaluationStorage.takeLast(n)
        }
}