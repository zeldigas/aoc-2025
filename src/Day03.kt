fun main() {
    fun part1(input: List<String>): Int {
        fun findValue(line: String): Int {
            var l = 0
            var r = 0
            var n2Index = 1
            while (r < line.length - 2) {
                r++
                if (line[l] < line[r]) {
                    l = r
                    n2Index = r + 1
                } else if (line[n2Index] < line[r]) {
                    n2Index = r
                }
            }
            r++
            if (line[n2Index] < line[r]) {
                n2Index = r
            }
            return buildString { append(line[l]); append(line[n2Index]) }.toInt()
        }
        return input.sumOf { findValue(it) }
    }

    fun part2(input: List<String>): Long {
        fun StringBuilder.makeNum(line: String, start: Int, left: Int, char: Char, maxChar:Char) {
            if (left == 0) return
            if (start == line.length - left) {
                append(line.substring(start))
                return
            }

            val index = line.indexOf(char, start)
            if (index == -1) {
                makeNum(line, start, left, char -1, if (char == maxChar) char - 1 else maxChar)
            } else if (index > line.length - left) {
                makeNum(line, start, left, char - 1, maxChar)
            } else {
                append(line[index])
                makeNum(line, index + 1, left - 1, maxChar, maxChar)
            }
        }

        fun findValue(line: String): Long {
            return buildString {
                makeNum(line, 0, 12, '9', '9')
            }.toLong()//.also { println(it) }
        }
        return input.sumOf { findValue(it) }
    }

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619L)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
