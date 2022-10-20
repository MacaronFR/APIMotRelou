package fr.imacaron.motrelou

import fr.imacaron.motrelou.depot.DepotMot
import fr.imacaron.motrelou.depot.ExceptionMotIntrouvableDepot
import fr.imacaron.motrelou.type.TDefinition
import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot
import java.time.LocalDateTime

class DepotMotMock: DepotMot{
	companion object {
		val liste = listOf(
			TMot("Exact", "Denis", LocalDateTime.now(), mutableListOf(TDefinition("Définition", "Denis", LocalDateTime.now(), 1))),
			TMot("Partiel1", "Denis", LocalDateTime.now(), mutableListOf(TDefinition("Définition", "Denis", LocalDateTime.now(), 1))),
			TMot("Partiel2", "Denis", LocalDateTime.now(), mutableListOf(TDefinition("Définition", "Denis", LocalDateTime.now(), 1))),
			TMot("Partiel3", "Denis", LocalDateTime.now(), mutableListOf(TDefinition("Définition", "Denis", LocalDateTime.now(), 1))),
			TMot("Partiel4", "Denis", LocalDateTime.now(), mutableListOf(TDefinition("Définition", "Denis", LocalDateTime.now(), 1))),
			TMot("1Partiel", "Denis", LocalDateTime.now(), mutableListOf(TDefinition("Définition", "Denis", LocalDateTime.now(), 1)))
		)
	}

	override fun recherche(demande: String, limit: Int, page: Int): List<TMot> {
		val search = if(limit >= liste.size){
			liste
		}else if(limit * page >= liste.size){
			emptyList<TMot>()
		}else if(limit * (page+1) >= liste.size){
			liste.subList(limit * page, liste.size - 1)
		}else{
			liste.subList(limit*page, limit*(page+1))
		}
		return search.filter { it.mot.contains(demande) }
	}

	override fun recuperer(mot: String): TMot = liste.find { it.mot == mot } ?: throw ExceptionMotIntrouvableDepot()

	override fun recuperer(limit: Int, page: Int): List<TMot> {
		TODO("Not yet implemented")
	}

	override fun modifier(mot: String, maj: TMajMot): TMot {
		TODO("Not yet implemented")
	}

	override fun supprimer(mot: String) {
		TODO("Not yet implemented")
	}

	override fun aleatoire(): TMot {
		TODO("Not yet implemented")
	}

	override fun ajouter(mot: TNouveauMot): TMot {
		TODO("Not yet implemented")
	}

}