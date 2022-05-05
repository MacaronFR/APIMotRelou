package fr.imacaron.motrelou.data.types

import fr.imacaron.motrelou.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TMot(
	val mot: String,
	var createur: String,
	@Serializable(with = LocalDateTimeSerializer::class)
	var creation: LocalDateTime,
	val definitions: MutableList<TDefinition>
)
