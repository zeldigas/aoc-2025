import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Long {
        fun findFake(left: String, right: String): List<Long> {
            val result = mutableListOf<Long>()
            val leftLen = left.length
            val rightLen = right.length
            val validRange = left.toLong()..right.toLong()
            for (i in leftLen..rightLen) {
                if (i % 2 != 0) continue
                val start = firstNumberWithDigits(i / 2)
                val end = start * 10
                for (j in (start until end)) {
                    val num = j * end + j
                    if (num in validRange) {
                        result.add(num)
                    }
                }
            }
            return result
        }

        var result: Long = 0
        input.forEach {
            val (left, right) = it.split("-")
            findFake(left, right).forEach { fake -> result += fake }
        }
        return result
    }

    fun part2(input: List<String>): Long {
        fun findFake(left: String, right: String): List<Long> {
            val result = mutableSetOf<Long>()
            val validRange = left.toLong()..right.toLong()
            val upTo = firstNumberWithDigits(digits(validRange.last) / 2 + 1)
            for (j in (1 until upTo)) {
                val digits = digits(j)
                val shift = 10.toDouble().pow(digits).toLong()
                var value = j * shift + j
                while (value <= validRange.last) {
                    if (value in validRange) {
                        result.add(value)
                    }
                    value = value * shift + j
                }

            }
            return result.toList()
        }

        var result: Long = 0
        input.forEach {
            val (left, right) = it.split("-")
            findFake(left, right).forEach { fake -> result += fake }
        }
        return result
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput[0].split(',')) == 1227775554L)
    check(part2(testInput[0].split(',')) == 4174379265L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input[0].split(',')).println()
    part2(input[0].split(',')).println()
}
