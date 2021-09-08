package com.example.plugins

import com.example.models.EvalRequest
import com.example.models.ExpressionErrorModel
import com.example.models.ExpressionResultModel
import com.example.models.History
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
  // Starting point for a Ktor app:
  routing {
    route("/history") {
      get {
        val n = call.request.queryParameters["n"]?.toIntOrNull() ?: return@get call.respondText(
          "Missing or malformed number of history entries to show",
          status = HttpStatusCode.BadRequest
        )
        //TODO - retrieve from data base
        call.respond(
          History(
            listOf(
              ExpressionResultModel("2", 2.0),
              ExpressionErrorModel("kek", "no keks allowed here")
            )
          )
        )
      }
    }
    post("/eval") {
      val evalRequest = call.receive<EvalRequest>()
      try {
        val result = EvaluatingHandler.evaluateExpression(evalRequest)
        //TODO - store to db
        call.respondText("$result", status = HttpStatusCode.Created)
      } catch (e: Exception) {
        call.respondText("error", status = HttpStatusCode.BadRequest)
      }
    }
  }
}
