package com.wasmflow

import com.dylibso.chicory.wasm.WasmModule as Module

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmModule internal constructor(
    internal val module: Module
)
