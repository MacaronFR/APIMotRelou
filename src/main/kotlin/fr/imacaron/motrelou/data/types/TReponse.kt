package fr.imacaron.motrelou.data.types

import kotlinx.serialization.Serializable

@Serializable
data class TReponse(
	val code: Int,
	val message: String,
	val donnee: String? = null
)
