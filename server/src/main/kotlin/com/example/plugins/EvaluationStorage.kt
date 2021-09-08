package com.example.plugins

import com.example.models.Expression

object EvaluationStorage {
  private val kek = mutableListOf<Expression>()

  fun put(expr: Expression) {
    //TODO
    kek.add(expr)
  }

  fun takeLast(n: Int): List<Expression> {
    //TODO
    return kek.takeLast(n)
  }
}