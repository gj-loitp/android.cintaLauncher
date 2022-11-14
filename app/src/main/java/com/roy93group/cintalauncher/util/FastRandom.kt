package com.roy93group.cintalauncher.util

class FastRandom(
    private var seed: Long
) {
    private fun random64(seed: Long): Long {
        var n = seed
        n = n xor (n shl 21)
        n = n xor (n ushr 35)
        n = n xor (n shl 4)
        return n
    }

    fun nextLong(): Long = random64(seed).also { seed = seed ushr 1 xor seed * 31 }

    @Suppress("unused")
    fun nextInt(): Int {
        val l = nextLong()
        return (l shr 32 xor l).toInt()
    }

    @Suppress("unused")
    fun nextIntPair(): Pair<Int, Int> {
        val l = nextLong()
        return (l shr 32).toInt() to l.toInt()
    }
}
