package com.wasmflow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@ExperimentalWasmApi
expect class WasmExportFn {
    operator fun invoke(vararg args: Long): LongArray?
}
