package fr.imacaron.motrelou.data.types

import kotlinx.serialization.Serializable

@Serializable
data class TMajMot(
	val mot: String? = null,
	val createur: String? = null
)
