package fr.imacaron.motrelou

import fr.imacaron.motrelou.depot.ExceptionMotIntrouvableDepot
import fr.imacaron.motrelou.domaine.ServiceMot
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ServiceMotTest {
	val service = ServiceMot(DepotMotMock(), LoggerFactory.getLogger("Test"))

	@Test
	fun rechercheNoMatch(){
		assertEquals(listOf(), service.recherche("Inexistant"))
	}

	@Test
	fun rechercheMatchExact(){
		assertEquals(DepotMotMock.liste.filter { it.mot == "Exact" }, service.recherche("Exact"))
	}

	@Test
	fun rechercheMatchPartiel(){
		assertEquals(DepotMotMock.liste.filter { it.mot != "Exact" }, service.recherche("Partiel"))
	}

	@Test
	fun recupererValid(){
		assertEquals(DepotMotMock.liste.find { it.mot == "Exact" }!!, service.recuperer("Exact"))
	}

	@Test
	fun recupererInvalid(){
		assertThrows<ExceptionMotIntrouvableDepot>{
			service.recuperer("Invalid")
		}
	}
}