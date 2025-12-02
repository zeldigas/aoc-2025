fun main() {
    fun part1(input: List<String>): Int {
        var zeroCounter = 0
        var arrow = 50
        input.forEach { step ->
            val direction = step[0]
            val amount = step.substring(1).toInt() % 100
            when (direction) {
                'L' -> {
                    arrow -= amount
                    if (arrow < 0) {
                        arrow += 100
                    }
                }

                'R' -> {
                    arrow += amount
                    arrow %= 100
                }
            }
            if (arrow == 0) {
                zeroCounter++
            }
        }
        return zeroCounter
    }

    fun part2(input: List<String>): Int {
        var zeroCounter = 0
        var arrow = 50
        input.forEach { step ->
            val direction = step[0]
            val totalTurns = step.substring(1).toInt()
            val full360Turns = totalTurns / 100
            zeroCounter += full360Turns // each 360 turn will cross 0
            val amount = totalTurns % 100
            when (direction) {
                'L' -> {
                    val prevValue = arrow
                    arrow -= amount
                    if (arrow < 0) {
                        arrow += 100
                        if (prevValue != 0) {
                            zeroCounter++
                        }
                    }
                }

                'R' -> {
                    arrow += amount
                    if (arrow > 100) {
                        zeroCounter++
                    }
                    arrow %= 100
                }
            }
            if (arrow == 0) {
                zeroCounter++
            }
        }
        return zeroCounter
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
