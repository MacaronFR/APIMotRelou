package fr.imacaron.motrelou.bdd

import fr.imacaron.motrelou.depot.DepotMot
import fr.imacaron.motrelou.depot.NotFoundException
import fr.imacaron.motrelou.getTDefinition
import fr.imacaron.motrelou.getTMot
import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot
import java.sql.Connection
import java.sql.DriverManager
import java.util.Random

class BddMot: DepotMot {

	companion object {
		val connection: Connection = DriverManager.getConnection(System.getenv("BDD_URL")!!, System.getenv("BDD_USER")!!, System.getenv("BDD_PASSWORD"))
		const val fields = "mot, MOTS.creation, MOTS.createur, definition, DEFINITIONS.creation, DEFINITIONS.createur, `index`"
	}

	override fun recherche(demande: String, limit: Int, page: Int): List<TMot> {
		val stmt = connection.prepareStatement("SELECT $fields FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot WHERE mot LIKE ? LIMIT ${page*limit}, $limit")
		stmt.setString(1, "%$demande%")
		val res = stmt.executeQuery()
		val list = mutableListOf<TMot>()
		while(res.next()){
			list.find { it.mot == res.getString("mot")!! }?.let{ mot ->
				res.getString("definition")?.let{
					mot.definitions.add(res.getTDefinition())
				}
			} ?: run{
				list.add(res.getTMot())
			}
		}
		return list
	}

	override fun recuperer(mot: String): TMot? {
		val stmt = connection.prepareStatement("SELECT $fields FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot WHERE mot = ?")
		println(mot)
		stmt.setString(1, mot)
		val res = stmt.executeQuery()
		return if(res.next()){
			res.getTMot()
		}else{
			null
		}
	}

	override fun recuperer(limit: Int, page: Int): List<TMot> {
		val stmt = connection.createStatement()
		val res = stmt.executeQuery("SELECT mot, MOTS.creation, MOTS.createur, definition, DEFINITIONS.creation, DEFINITIONS.createur, `index` FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot LIMIT ${page*limit}, $limit")
		val list = mutableListOf<TMot>()
		while(res.next()){
			list.find { it.mot == res.getString("mot")!! }?.let{ mot ->
				res.getString("definition")?.let{
					mot.definitions.add(res.getTDefinition())
				}
			} ?: run{
				list.add(res.getTMot())
			}
		}
		return list
	}

	override fun modifier(mot: String, maj: TMajMot): TMot {
		val preMot = recuperer(mot) ?: throw NotFoundException("Le mot n'existe pas")
		val stmt = connection.prepareStatement("UPDATE MOTS SET mot = ?, createur = ? WHERE mot = ?")
		stmt.setString(1, maj.mot ?: preMot.mot)
		stmt.setString(2, maj.createur ?: preMot.createur)
		stmt.setString(3, mot)
		stmt.executeUpdate()
		return recuperer(maj.mot ?: mot)!!
	}

	override fun supprimer(mot: String): Boolean {
		val stmt = connection.prepareStatement("DELETE FROM MOTS WHERE mot = ?")
		stmt.setString(1, mot)
		return stmt.executeUpdate() != 0
	}

	fun count(): Int{
		return connection.createStatement().executeQuery("SELECT COUNT(id_mot) as tot FROM MOTS").let {
			it.next()
			it.getInt("tot")
		}
	}

	override fun aleatoire(): TMot {
		return connection.createStatement().executeQuery("SELECT $fields FROM MOTS LEFT JOIN DEFINITIONS ON MOTS.id_mot = DEFINITIONS.id_mot GROUP BY mot LIMIT ${Random().nextInt(0, count())}, 1").let{
			it.next()
			it.getTMot()
		}
	}

	override fun ajouter(mot: TNouveauMot): TMot {
		var stmt = connection.prepareStatement("INSERT INTO MOTS (mot, createur) VALUES (?, ?)")
		stmt.setString(1, mot.mot)
		stmt.setString(2, mot.createur)
		stmt.executeUpdate()
		stmt = connection.prepareStatement("SELECT id_mot FROM MOTS WHERE mot = ?")
		stmt.setString(1, mot.mot)
		val idMot = stmt.executeQuery().let{
			it.next()
			it.getInt("id_mot")
		}
		stmt = connection.prepareStatement("INSERT INTO DEFINITIONS (definition, createur, id_mot, `index`) VALUES (?, ?, ?, 1)")
		stmt.setString(1, mot.definition)
		stmt.setString(2, mot.createur)
		stmt.setInt(3, idMot)
		stmt.execute()
		stmt = connection.prepareStatement("SELECT $fields, MOTS.id_mot FROM MOTS LEFT JOIN DEFINITIONS ON MOTS.id_mot = DEFINITIONS.id_mot WHERE mot = ?")
		stmt.setString(1, mot.mot)
		return stmt.executeQuery().let{
			it.next()
			it.getTMot()
		}
	}
}