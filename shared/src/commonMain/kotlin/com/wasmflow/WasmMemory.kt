package com.wasmflow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@ExperimentalWasmApi
expect class WasmMemory {
    fun readString(address: Int, length: Int, charset: String = "UTF-8"): String
}

@ExperimentalWasmApi
fun WasmMemory.readString(args: LongArray, offset: Int = 0) = run {
    val (addr, len) = args.takePair(offset).asIntPair()
    readString(addr, len)
}
