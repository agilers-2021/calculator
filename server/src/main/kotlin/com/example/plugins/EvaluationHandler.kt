package com.example.plugins

import com.example.models.EvalRequest
import com.example.models.Expression
import com.example.models.ExpressionErrorModel
import com.example.models.ExpressionResultModel
import java.lang.Exception
import org.mariuszgromada.math.mxparser.Expression as Expr

object EvaluationHandler {
  /**
   * Evaluates expression
   * @return ExpressionResultModel if ho errors occurred and ExpressionErrorModel instead
   */
  fun evaluateExpression(evalRequest: EvalRequest): Expression {
    if (!validate(evalRequest.str)) {
      return ExpressionErrorModel(evalRequest.str, "Wrong string format")
    }
    val res: Double
    try {
      res = Expr(evalRequest.str).calculate()
    } catch (e: Exception) {
      return ExpressionErrorModel(evalRequest.str, "Error in evaluation")
    }
    return ExpressionResultModel(evalRequest.str, res)
  }

  fun validate(expr: String): Boolean {
    return expr.matches(Regex("[-0-9+*/%.^() ]*"))
  }
}