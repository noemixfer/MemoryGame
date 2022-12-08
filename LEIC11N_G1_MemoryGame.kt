/*
* DISCIPLINA: PROGRAMAÇÃO
* DOCENTE: PAULA GRAÇA
* 1º TRABALHO DE GRUPO
*
* GRUPO 1
* Micaela Macatrão, nº 39734
* João Marques, nº 49212
* Noemi Ferreira, nº 49226
*
* OUTUBRO DE 2022
*/

val SYMBOLS = "#%XO&"

fun main() {
    val pairs = (SYMBOLS+SYMBOLS).toList().shuffled()
    //println(pairs)
    println("Foram gerados ${SYMBOLS.length} pares aleatórios de símbolos.")
    println("Vamos jogar!")
    var places: List<Char> = allHiddenPairs(pairs)
    var trys = 0
    do {
        println("$trys: $places")
        val first = readPosition("primeira")
        val second = readPosition("segunda")
        if ( isValidPositions(first, second, places) ) {
            places = places.play(first, second, pairs)
            trys++
        } else println("Posições inválidas")
    } while( ! isAllPairsShowed(places) )
    println("$trys: $places")
    println("Terminou o jogo usando $trys tentativas")
}

/**
 * Flips the two elements at positions [p1] and [p2].
 * If they do not form a pair, display them for 4 seconds and then hide.
 * @receiver Original places
 * @param p1 First position to flip.
 * @param p2 Second position to flip.
 * @param pairs The original pairs
 * @return The turned places if there was a pair or the original places if not.
 */
fun List<Char>.play(p1: Int, p2: Int, pairs: List<Char>): List<Char> {
    val turned = turnPlaces(p1, p2, pairs)
    if (turned[p1] != turned[p2]) {
        print(turned)
        repeat(4) { print('.'); Thread.sleep(1000) }
        print("\r                                                       \r")
        return this
    }
    return turned
}

/**
 * Generates a list of chars with the same dimension as [pairs] but only with '_' chars.
 * @param pairs The original pairs
 * @return The generated list
 */
fun allHiddenPairs(pairs: List<Char>): List<Char> =
    List(pairs.size) { '_' }

/**
 * Reads an integer value after asking the question in form: "Indique a <name> posição a virar ? "
 * @param name Name of position to read
 * @return The value of position read
 */
fun readPosition(name: String): Int {
    print("Indique a $name posição a virar: ")
    return readln().toInt()
}

/**
 * Checks if the positions [p1] and [p2] are valid, that is,
 * if they are within the limits and the places of these positions are not yet visible.
 * @param p1 The first position
 * @param p2 The second position
 * @param places Actual places
 * @return true if both places are valid and not equals
 */
fun isValidPositions(p1: Int, p2:Int, places: List<Char>): Boolean =
    (p1 != p2
    && p1 in places.indices
    && p2 in places.indices
    && places[p1] == '_'
    && places[p2] == '_')

/**
 * Indicates whether all pairs in [places] are visible.
 * @param places
 */
fun isAllPairsShowed(places: List<Char>): Boolean =
    '_' !in places

/**
 * Flips the elements into positions [p1] and [p2].
 * Showing them if they are hidden or hiding them if they are showing.
 * @receiver The current places
 * @param p1 A position to turn
 * @param p2 Another position to turn
 * @param pairs The original pairs
 * @return Places after turning
 */
fun List<Char>.turnPlaces(p1: Int, p2: Int, pairs: List<Char>): List<Char> =
    this.mapIndexed { idx, value ->
        if (idx == p1) pairs[p1]
        else if (idx == p2) pairs[p2]
        else this[idx]}
