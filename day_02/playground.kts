// Run script via command such as `kotlinc -script playground.kts "line1\nline2"`
val colorsAvailable = mapOf("red" to 12, "green" to 13, "blue" to 14)

fun cubeConundrum(input: String): Pair<Int, Int> {
    var sumOfPossibleIds = 0
    var sumOfPower = 0

    input.lineSequence().forEach { gameLine ->
        val gameId = gameLine.substringAfter(" ").substringBefore(":").toInt()
        var isPossible = true
        val colorMaxes = mutableMapOf<String, Int>()

        gameLine.substringAfter(":").split(";").forEach { handful ->
            val colorsInHandful = handful.split(",").map { it.trim() }

            colorsInHandful.forEach {
                val count = it.substringBefore(" ").toInt()
                val color = it.substringAfter(" ")
                if (count > colorsAvailable.getValue(color)) {
                    isPossible = false
                }
                if (count > 0 && colorMaxes.getOrDefault(color, 0) < count) {
                    colorMaxes[color] = count
                }
            }
        }

        if (isPossible) { sumOfPossibleIds += gameId }
        sumOfPower += colorMaxes.values.reduce { acc, i ->  acc * i }
    }
    return Pair(sumOfPossibleIds, sumOfPower)
}

// Pass Input here
val result = cubeConundrum(args[0])
println("Sum of possible game ids: ${result.first}")
println("Sum of the power of the sets ${result.second}")