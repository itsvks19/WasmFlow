package com.wasmflow

import kotlinx.io.RawSource

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
@WasmDsl
actual class WasmModuleScope actual constructor() {
    actual fun source(source: RawSource): WasmModule {
        TODO("Not yet implemented")
    }

    actual fun bytes(bytes: ByteArray): WasmModule {
        TODO("Not yet implemented")
    }
}