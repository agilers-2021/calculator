package com.example.plugins

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.example.models.Expression
import com.example.models.ExpressionErrorModel
import com.example.models.ExpressionResultModel

object EvaluationStorage {

  object ExpressionTable : Table() {
    val id = integer("id").autoIncrement()
    val expression = varchar("text", length=100)
    val result = double("result")
  }

  private val kek = mutableListOf<Expression>()

  fun put(expr: ExpressionResultModel) {
    ExpressionTable.insert {
      it[expression] = expr.expression
      it[result] = expr.result
    }
  }

  fun takeLast(n: Int): List<ExpressionResultModel> {
    return ExpressionTable.selectAll().map { ExpressionResultModel(it[ExpressionTable.expression], it[ExpressionTable.result]) }
  }

  fun init() {
    SchemaUtils.create(ExpressionTable)
  }
}