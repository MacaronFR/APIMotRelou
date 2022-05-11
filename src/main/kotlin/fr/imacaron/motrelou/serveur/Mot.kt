package fr.imacaron.motrelou.serveur

import fr.imacaron.motrelou.bdd.BddMot
import fr.imacaron.motrelou.domaine.ServiceMot
import fr.imacaron.motrelou.installCORS
import fr.imacaron.motrelou.installCallLogging
import fr.imacaron.motrelou.installResources
import fr.imacaron.motrelou.requete.RequetesMot
import fr.imacaron.motrelou.respondJson
import fr.imacaron.motrelou.ressources.Mot
import fr.imacaron.motrelou.type.TReponse
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.mot(){
	installCORS()
	installCallLogging()
	installResources()
	val reqMot: RequetesMot = ServiceMot(BddMot(), log)
	routing {
		get<Mot>{
			try {
				call.request.queryParameters["recherche"]?.let{
					call.respondJson(reqMot.recherche(it, call.request.queryParameters["page"]?.toInt() ?: 0))
				} ?: run{
					call.respondJson(reqMot.recherche(null, call.request.queryParameters["page"]?.toInt() ?: 0))
				}
			}catch(_: NumberFormatException){
				call.respondJson(TReponse(400, "Le numéro de page n'est pas un entier"), 400)
			}
		}
	}
}