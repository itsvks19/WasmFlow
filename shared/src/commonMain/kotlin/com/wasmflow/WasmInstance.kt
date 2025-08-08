package com.wasmflow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@ExperimentalWasmApi
expect class WasmInstance {
    val memory: WasmMemory
    fun function(name: String): WasmExportFn
}
