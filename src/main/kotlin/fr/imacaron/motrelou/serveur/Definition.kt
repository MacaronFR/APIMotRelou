package fr.imacaron.motrelou.serveur

import fr.imacaron.motrelou.getBodyTyped
import fr.imacaron.motrelou.requete.RequetesDefinition
import fr.imacaron.motrelou.respondJson
import fr.imacaron.motrelou.ressources.Mot
import fr.imacaron.motrelou.type.TReponse
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.resources.put
import io.ktor.server.resources.post
import io.ktor.server.routing.*

fun Application.definition(reqDef: RequetesDefinition){
	routing {
		post<Mot.Id.Definition>{
			reqDef.creer(call.getBodyTyped(), it.parent.mot)?.let{ mot ->
				call.respondJson(mot, 201)
			} ?: run{
				call.respondJson(TReponse.Conflict)
			}
		}
		put<Mot.Id.Definition.Index>{
			reqDef.maj(call.getBodyTyped(), it.parent.parent.mot, it.index)?.let{mot ->
				call.respondJson(mot)
			} ?: run {
				call.respondJson(TReponse.NotFound)
			}
		}
		delete<Mot.Id.Definition.Index>{
			if(reqDef.delete(it.parent.parent.mot, it.index)){
				call.respondJson(TReponse.NoContent)
			}else{
				call.respondJson(TReponse.NotFound)
			}
		}
	}
}