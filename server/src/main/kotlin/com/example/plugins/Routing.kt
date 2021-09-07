package com.example.plugins

import com.example.models.Expression
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
  // Starting point for a Ktor app:
  routing {
    get("/history") {
      //TODO - retrieve from data base
      call.respondText("No history yet")
    }
    post("/eval") {
      val expression = call.receive<Expression>()
      try {
        val result = EvaluatingHandler.evaluateExpression(expression)
        //TODO - store to db
        call.respondText("$result", status = HttpStatusCode.Created)
      } catch (e: Exception) {
        call.respondText("error", status = HttpStatusCode.BadRequest)
      }
    }
  }
}
