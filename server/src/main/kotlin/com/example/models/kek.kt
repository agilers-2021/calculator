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
data class History(val entries: List<Expression>)