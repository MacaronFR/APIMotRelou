package fr.imacaron.motrelou.depot

/**
 * @author MacaronFR
 * @param message Le message de l'erreur
 * Classe d'erreur dans l'interaction avec le depot en cas de conflit
 */
class ConflictException(message: String): Throwable(message)

/**
 * @author MacaronFR
 * @param message Le message de l'erreur
 * Classe d'erreur dans l'interaction avec le depot quand la donnée demandée n'est pas trouvé
 */
class NotFoundException(message: String): Throwable(message)