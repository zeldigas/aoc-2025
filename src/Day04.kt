import kotlin.math.max
import kotlin.math.min

fun main() {
    fun isFree(matrix: Array<CharArray>, n: Int, m: Int, i: Int, j: Int): Boolean {
        if (matrix[i][j] != '@') return false

        val left = max(0, i - 1)
        val right = min(i + 1, n - 1)
        val top = max(0, j - 1)
        val bottom = min(m - 1, j + 1)

        var rolls = 0;
        for (ii in left..right) {
            for (jj in top..bottom) {
                if (ii == i && jj == j) continue

                if (matrix[ii][jj] == '@') {
                    rolls++
                }
            }
        }
        return rolls < 4
    }

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length

        val matrix = input.map { it.toCharArray() }.toTypedArray()

        return (0 until n).sumOf { i -> (0 until n).sumOf { j -> if (isFree(matrix, n, m, i, j)) 1 else 0 } }
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val matrix = input.map { it.toCharArray() }.toTypedArray()

        fun cleanup(): Int {
            var cnt = 0
            (0 until n).forEach { i ->
                (0 until n).forEach { j ->
                    if (isFree(matrix, n, m, i, j)) {
                        matrix[i][j] = '.'
                        cnt++
                    }
                }
            }
            return cnt
        }

        var totalRemoved = 0
        var iterations = 0
        while (true) {
            iterations++
            val next = cleanup()
            totalRemoved += next
            if (next == 0) break
        }

        return totalRemoved
    }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
