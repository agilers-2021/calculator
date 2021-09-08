package com.example.plugins

import org.jetbrains.exposed.sql.*
import com.example.models.Expression
import com.example.models.ExpressionResultModel

object EvaluationStorage {

  private var nextId = 1

  object ExpressionTable : Table() {
    val id = integer("id").primaryKey()
    val expression = varchar("text", length=100)
    val result = double("result")
  }

  private val kek = mutableListOf<Expression>()

  fun put(expr: ExpressionResultModel) {
    ExpressionTable.insert {
      it[id] = nextId
      nextId += 1
      it[expression] = expr.expression
      it[result] = expr.result
    }
  }

  fun takeLast(n: Int): List<ExpressionResultModel> {
    return ExpressionTable.selectAll().map { ExpressionResultModel(it[ExpressionTable.expression], it[ExpressionTable.result]) }.takeLast(n)
  }

  fun init() {
    SchemaUtils.create(ExpressionTable)
    nextId = ExpressionTable.selectAll().toList().size + 1
  }
}