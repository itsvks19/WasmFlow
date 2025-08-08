package com.wasmflow

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmMemory {
    actual fun readString(
        address: Int,
        length: Int,
        charset: String
    ): String {
        TODO("Not yet implemented")
    }
}
