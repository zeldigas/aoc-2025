import java.math.BigInteger

enum class Operator(
    val op: (List<Int>) -> Long
) {
    ADD({ it.fold(0L) { acc, v -> acc + v } }),
    MULT({ it.fold(1L) { acc, v -> acc * v } });

    companion object {
        fun from(opCode: Char) = when (opCode) {
            '+' -> ADD
            '*' -> MULT
            else -> throw IllegalArgumentException("Unknown operator: $opCode")
        }
    }
}

fun main() {

    data class Block(val op: Operator, val start: Int, val end: Int)

    fun Block.parse(l: List<String>) = buildList {
        for (i in start..end) {
            var n = 0
            (0..<l.size).forEach {
                if (i < l[it].length && l[it][i] != ' ') {
                    n = n * 10 + (l[it][i] - '0')
                }
            }
            add(n)
        }
    }

    fun part1(input: List<String>): Long {
        val operators = input.last().toCharArray().filterNot { it == ' ' }
        val numbers = input.dropLast(1).map { l ->
            l.split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
        }
        val results = operators.mapIndexed { idx, op ->
            Operator.from(op).op(numbers.map { l -> l[idx] })
        }
        return results.fold(0L) { r, v -> r + v }
    }

    fun part2(input: List<String>): Long {
        val lines = input.dropLast(1)
        val blocks: Sequence<Block> = sequence {
            val opLine = input.last()
            var i = 0
            while (i < opLine.length) {
                if (opLine[i] != ' ') {
                    val op = opLine[i]
                    val start = i
                    var length = 1
                    while ((start + length) < opLine.length && opLine[start + length] == ' ') {
                        length++
                    }
                    if (start + length == opLine.length) {
                        //last operator does have span of spaces after to align with numbers, so calculate it separately
                        val maxLength = lines.maxOfOrNull { l -> l.length }!! - start
                        yield(Block(Operator.from(op), start, start + maxLength - 1))
                    } else {
                        yield(Block(Operator.from(op), start, start + length - 2))
                    }
                    i += length
                }
            }
        }
        return blocks.fold(0L) { res, b ->
            val nums = b.parse(lines)
            val v = b.op.op(nums)
            res + v
        }
    }

    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
