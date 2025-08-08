package com.wasmflow

fun LongArray.takePair(offset: Int = 0): Pair<Long, Long> {
    return get(offset) to get(offset + 1)
}

fun LongArray.takeTriple(offset: Int = 0): Triple<Long, Long, Long> {
    return Triple(get(offset), get(offset + 1), get(offset + 2))
}

fun Pair<Long, Long>.asIntPair(): Pair<Int, Int> {
    return first.i32 to second.i32
}

fun Triple<Long, Long, Long>.asIntTriple(): Triple<Int, Int, Int> {
    return Triple(first.i32, second.i32, third.i32)
}
