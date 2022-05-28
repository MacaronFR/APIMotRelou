package fr.imacaron.motrelou.serveur

import fr.imacaron.motrelou.*
import fr.imacaron.motrelou.bdd.BddMot
import fr.imacaron.motrelou.domaine.ServiceMot
import fr.imacaron.motrelou.requete.RequetesMot
import fr.imacaron.motrelou.ressources.Mot
import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TNouveauMot
import fr.imacaron.motrelou.type.TReponse
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.put
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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
		get<Mot.Id>{
			reqMot.recuperer(it.mot)?.let {res ->
				call.respondJson(res)
			} ?: run {
				call.respondJson(TReponse.NotFound)
			}
		}
		put<Mot.Id>{
			val test = call.receiveText()
			val maj = Json.decodeFromString<TMajMot>(test)
			reqMot.maj(it.mot, maj)?.let {
				call.respondJson(it)
			} ?: run{
				call.respondJson(TReponse.NotFound)
			}
		}
		delete<Mot.Id>{
			if(reqMot.supprimer(it.mot)){
				call.respondJson(TReponse.NoContent)
			}else{
				call.respondJson(TReponse.NotFound)
			}
		}
		get<Mot.Random>{
			call.respondJson(reqMot.aleatoire())
		}
		post<Mot>{
			call.getBodyTyped<TNouveauMot>().let {
				reqMot.recuperer(it.mot)?.let {
					call.respondJson(TReponse.Conflict)
				}
				reqMot.creer(it)?.let{ mot ->
					call.respondJson(mot, 201)
				} ?: run {
					call.respondJson(TReponse.Conflict)
				}
			}
		}
	}
}