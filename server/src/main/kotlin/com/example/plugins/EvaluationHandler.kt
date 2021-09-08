package com.example.plugins

import com.example.models.EvalRequest
import com.example.models.Expression
import com.example.models.ExpressionResultModel

object EvaluationHandler {
  /**
   * Evaluates expression
   * @return ExpressionResultModel if ho errors occurred and ExpressionErrorModel instead
   */
  fun evaluateExpression(evalRequest: EvalRequest): Expression {
    //TODO
    return ExpressionResultModel(evalRequest.str, 0.0)
  }
}