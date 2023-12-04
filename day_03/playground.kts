// Run script via command such as `kotlinc -script playground.kts 'line1\nline2'`

/* ------ Part 1 ------ */
fun part1(input: String): Int {
    var total = 0
    val schematic = input.lines().map { line -> line.toList() }

    schematic.forEachIndexed { yIndex, line ->
        line.forEachIndexed { xIndex, _ ->

            if (isFirstDigit(xIndex, yIndex, schematic)) {
                val numberString = extractFullNumberString(xIndex, line)
                val minX = (xIndex - 1).coerceAtLeast(0)
                val maxX = (xIndex + numberString.length + 1).coerceAtMost(line.size)

                val topResult = schematic.getOrNull(yIndex - 1)?.subList(minX, maxX)?.indexOfFirst {
                    isSpecialChar(it)
                }?.let { it >= 0 } ?: false

                val middleResult = isSpecialChar(line.getOrNull(xIndex - 1)) || isSpecialChar(line.getOrNull(xIndex + numberString.length))

                val bottomResult = schematic.getOrNull(yIndex + 1)?.subList(minX, maxX)?.indexOfFirst {
                    isSpecialChar(it)
                }?.let { it >= 0 } ?: false

                if (topResult || middleResult || bottomResult) {
                    total += numberString.toInt()
                }
            }
        }
    }
    return total
}

fun isSpecialChar(char: Char?) = char != null && !char.isDigit() && char != '.'

fun isFirstDigit(x: Int, y: Int, schematic: List<List<Char>>): Boolean {
    val previousCharIsDigit = if (x-1 >= 0) schematic[y][x-1].isDigit() else false
    return !previousCharIsDigit && schematic[y][x].isDigit()
}

fun extractFullNumberString(x: Int, line: List<Char>): String {
    return line.subList(x, line.size)
            .joinToString("")
            .takeWhile { it.isDigit() }
}

/* ------ Part 2 ------ */
fun part2(input: String): Int {
    var total = 0
    val schematic = input.lines().map { it.toList() }

    schematic.forEachIndexed { yIndex, line ->
        line.forEachIndexed { xIndex, char ->
            if (isAsterisk(char)) {
                val surroundingNumbers = getSurroundingNumbers(xIndex, yIndex, schematic)
                if (surroundingNumbers.size > 1) {
                    total += surroundingNumbers.reduce { acc, i ->  acc * i }
                }
            }
        }
    }
    return total
}

data class Coordinate(val x: Int, val y: Int)

fun isAsterisk(char: Char?) = char == '*'

fun getSurroundingNumbers(x: Int, y: Int, schematic: List<List<Char>>): List<Int> {
    val surroundingDigitCoordinates = listOf(
            Coordinate(x-1, y-1), Coordinate(x, y-1), Coordinate(x+1, y-1),
            Coordinate(x-1, y), Coordinate(x+1, y),
            Coordinate(x-1, y+1), Coordinate(x, y+1), Coordinate(x+1, y+1)
    ).filter { schematic.getOrNull(it.y)?.getOrNull(it.x)?.isDigit() == true }

    val coordinatesOfFirstDigit = surroundingDigitCoordinates.map {
        schematic[it.y].let { line ->
            var firstXDigit = it.x
            while (line.getOrNull(firstXDigit-1)?.isDigit() == true) {
                firstXDigit -= 1
            }
            Coordinate(firstXDigit, it.y)
        }
    }.toSet()

    val numbers = coordinatesOfFirstDigit.map {
        val line = schematic[it.y]
        val lineAtStartOfX = line.subList(it.x, line.size)
        lineAtStartOfX.takeWhile { it.isDigit() }.joinToString(separator = "").toInt()
    }
    return numbers
}

// Pass Input here
val part1Result = part1(args[0])
println("Part 1 Answer: $part1Result")

val part2Result = part2(args[0])
println("Part 2 Answer: $part2Result")
