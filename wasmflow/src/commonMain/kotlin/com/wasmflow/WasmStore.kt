package com.wasmflow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@ExperimentalWasmApi
expect class WasmStore {
    fun instantiate(name: String, module: WasmModule): WasmInstance
    fun addFunction(vararg functions: WasmHostFn): WasmStore
}

@ExperimentalWasmApi
expect fun WasmStore(): WasmStore
