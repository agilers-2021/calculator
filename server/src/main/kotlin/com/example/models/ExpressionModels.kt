package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class EvalRequest(val str: String)

@Serializable
sealed class Expression()

@Serializable
data class ExpressionResultModel(val expression: String, val result: Double) : Expression()

@Serializable
data class ExpressionErrorModel(val expression: String, val msg: String) : Expression()

@Serializable
data class HistoryEntry(val expression: String, val result: String)

@Serializable
data class History(val entries: List<HistoryEntry>) {
    companion object {
        fun create(entries: List<Expression>) = History(
            entries.map {
                when (it) {
                    is ExpressionResultModel -> HistoryEntry(it.expression, it.result.toString())
                    is ExpressionErrorModel -> HistoryEntry(it.expression, it.msg)
                }
            }
        )
    }
}