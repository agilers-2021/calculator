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
        val history = History(EvaluationStorage.takeLast(n))
        call.respond(history)
      }
    }
    post("/eval") {
      val evalRequest = call.receive<EvalRequest>()
      val result = EvaluationHandler.evaluateExpression(evalRequest)
      EvaluationStorage.put(result)
      when(result) {
        is ExpressionErrorModel -> call.respondText(result.msg, status = HttpStatusCode.BadRequest)
        is ExpressionResultModel -> call.respondText("${result.result}", status = HttpStatusCode.Created)
      }
    }
  }
}
