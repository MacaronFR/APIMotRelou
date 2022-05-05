package fr.imacaron.motrelou.data.types

import kotlinx.serialization.Serializable

@Serializable
data class TNouvelleDefinition(
	val definition: String,
	val createur: String
)