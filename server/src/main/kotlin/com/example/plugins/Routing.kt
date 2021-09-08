package com.example.plugins

import com.example.Master
import com.example.models.EvalRequest
import com.example.models.ExpressionErrorModel
import com.example.models.ExpressionResultModel
import com.example.models.History
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
  // Starting point for a Ktor app:
  routing {
    static {
      default("client/index.html")
      files("client")
    }
    route("/history") {
      get {
        val n = call.request.queryParameters["n"]?.toIntOrNull() ?: return@get call.respondText(
          "Missing or malformed number of history entries to show",
          status = HttpStatusCode.BadRequest
        )
        val history = History(Master.takeLast(n))
        call.respond(history)
      }
    }
    route("/eval") {
      post {
        val evalRequest = call.receive<EvalRequest>()
        val result = EvaluationHandler.evaluateExpression(evalRequest)
        if (result is ExpressionResultModel) {
          Master.put(result)
        }
        when (result) {
          is ExpressionErrorModel -> call.respondText(result.msg, status = HttpStatusCode.BadRequest)
          is ExpressionResultModel -> call.respondText("${result.result}", status = HttpStatusCode.Created)
        }
      }
    }
  }
}
