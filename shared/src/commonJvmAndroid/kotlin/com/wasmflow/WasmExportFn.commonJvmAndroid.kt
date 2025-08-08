package com.wasmflow

import com.dylibso.chicory.runtime.ExportFunction

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmExportFn internal constructor(
    private val function: ExportFunction
) {
    actual operator fun invoke(vararg args: Long): LongArray? = function.apply(*args)
}

@OptIn(ExperimentalWasmApi::class)
internal fun ExportFunction.toWasmExportFn() = WasmExportFn(this)
