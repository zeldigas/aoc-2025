fun main() {

    fun parseRanges(input: List<String>): List<LongRange> {
        val fresh = input.takeWhile { it.isNotBlank() }.map {
            val (l, r) = it.split('-').map { num -> num.toLong() }
            l..r
        }
        return fresh
    }

    fun part1(input: List<String>): Int {
        val fresh = parseRanges(input)
        val ids = input.drop(fresh.size + 1).map { it.toLong() }
        return ids.count { num -> fresh.any { num in it } }
    }

    fun part2(input: List<String>): Long {
        // idea - let's normalize ranges by splitting them to non-intersecting items and count sizes of normalized ranges
        fun LongRange.toNonIntersectingWith(other: LongRange): LongRange {
            return if (first < other.first) {
                first..<other.first
            } else {
                (other.last + 1)..last
            }
        }

        var fresh: List<LongRange> = parseRanges(input).sortedBy { it.first }.toMutableList()
        val normalized = mutableListOf<LongRange>()
        while (fresh.isNotEmpty()) {
            val head = fresh.first()
            val tail = fresh.drop(1)
            if (tail.any { head in it }) {
                fresh = tail//range already in other ranges, so we don't need to include it
            } else {
                normalized += head

                fresh = tail
                    .filter { it !in head } // if range from tail fully in head range, skip it
                    .map {
                        if (it.hasIntersection(head)) {
                            it.toNonIntersectingWith(head)
                        } else {
                            it
                        }
                    }
            }
        }
        return normalized.sumOf { it.size() }
    }

    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
