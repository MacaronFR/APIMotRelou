package fr.imacaron.motrelou.ressources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/mot")
class Mot{
	@Serializable
	@Resource("random")
	class Random(val parent: Mot = Mot())

	@Serializable
	@Resource("{mot}")
	class Id(val parent: Mot = Mot(), val mot: String)
}