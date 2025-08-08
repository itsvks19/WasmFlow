package com.wasmflow

import kotlinx.io.RawSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@ExperimentalWasmApi
@WasmDsl
expect class WasmModuleScope() {
    fun source(source: RawSource): WasmModule
    fun bytes(bytes: ByteArray): WasmModule
}
