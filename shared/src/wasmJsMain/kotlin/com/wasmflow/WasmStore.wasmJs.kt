package com.wasmflow

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmStore internal constructor(val temp: String) {
    actual fun instantiate(
        name: String,
        module: WasmModule
    ): WasmInstance {
        TODO("Not yet implemented")
    }

    actual fun addFunction(vararg functions: WasmHostFn): WasmStore {
        TODO("Not yet implemented")
    }
}

@ExperimentalWasmApi
actual fun WasmStore(): WasmStore {
    TODO("Not yet implemented")
}
