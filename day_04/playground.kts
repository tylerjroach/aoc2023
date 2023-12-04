// Run script via command such as `kotlinc -script playground.kts 'line1\nline2'`
data class Card(val index: Int, val winningNumbers: Set<String>, val numbers: Set<String>)

fun getCards(input: String): List<Card> {
    return input.lines().mapIndexed { index, line ->
        val card = line.substringAfter(": ").split("|")
        val winningNumbers = card[0].trim().split(" ").filterNot { it.isBlank() }.toSet()
        val numbers = card[1].trim().split(" ").filterNot { it.isBlank() }.toSet()
        Card(index, winningNumbers, numbers)
    }
}

// counts number of wins
fun getWinCount(card: Card): Int {
    return card.numbers.fold(0) { acc, s ->
        if (card.winningNumbers.contains(s)) {
            acc + 1
        } else {
            acc
        }
    }
}

fun part1(input: String): Int {
    return getCards(input).map {
        it.numbers.fold(0) { acc, s ->
            if (it.winningNumbers.contains(s)) {
                if (acc == 0) { 1 } else { acc * 2 }
            } else {
                acc
            }
        }
    }.sum()
}

fun part2(input: String): Int {
    val cards = getCards(input).toMutableList()

    // map of position / wins per position
    val winsByCardIndex = cards.map { card ->
        val winCount = getWinCount(card)
        Pair(card.index, winCount)
    }.associateBy( {it.first}, {it.second} )

    val cardCountPerIndex = winsByCardIndex.keys.associateWith { 1 }.toMutableMap()

    return cards.fold(0) { acc, card ->
        val cardWinCount = winsByCardIndex.getValue(card.index)
        val matchingCards = cardCountPerIndex.getValue(card.index)
        val nextCardIndex = card.index + 1
        for (i in nextCardIndex until (nextCardIndex + cardWinCount)) {
            if (i < cards.size) {
                cardCountPerIndex[i] = cardCountPerIndex.getValue(i) + matchingCards
            }
        }
        acc +matchingCards
    }
}

// Pass Input here
val part1Result = part1(real)
println("Part 1 Answer: $part1Result")

val part2Result = part2(real)
println("Part 2 Answer: $part2Result")