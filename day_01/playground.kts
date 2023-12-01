// Run script via command such as `kotlinc -script playground.kts "two1nine\n12"`
val numberConversionMap = mapOf(
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

/**
 * @param input a string of lines separated by \n
 */
fun calibrate(input: String): Int {
    return input.lineSequence().sumOf { line ->
        val keys = numberConversionMap.keys
        val first = numberConversionMap.getOrDefault(line.findAnyOf(keys)?.second, 0)
        val last = numberConversionMap.getOrDefault(line.findLastAnyOf(keys)?.second, 0)
        (first * 10) + last
    }
}

// Pass Input here
val sum = calibrate(args[0])
println(sum)
