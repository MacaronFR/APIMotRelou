package fr.imacaron.motrelou.data.types

import kotlinx.serialization.Serializable

@Serializable
data class TNouveauMot(
	val mot: String,
	val createur: String,
	val definition: List<TDefinition>? = null
)
