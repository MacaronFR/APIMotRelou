package fr.imacaron.motrelou.data.types

import fr.imacaron.motrelou.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TDefinition(
	var definition: String,
	val createur: String,
	@Serializable(with = LocalDateTimeSerializer::class)
	val creation: LocalDateTime,
	var index: Int
)
