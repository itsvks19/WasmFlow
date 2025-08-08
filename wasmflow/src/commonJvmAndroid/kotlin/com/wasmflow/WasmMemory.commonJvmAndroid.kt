package com.wasmflow

import com.dylibso.chicory.runtime.Memory
import java.nio.charset.Charset

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmMemory internal constructor(
    internal val memory: Memory
) {
    actual fun readString(
        address: Int,
        length: Int,
        charset: String
    ): String {
        return memory.readString(address, length, Charset.forName(charset))
    }
}
