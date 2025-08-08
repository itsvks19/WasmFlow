package com.wasmflow

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmInstance {
    actual val memory: WasmMemory
        get() = TODO("Not yet implemented")

    actual fun function(name: String): WasmExportFn {
        TODO("Not yet implemented")
    }
}
