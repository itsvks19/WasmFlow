package com.wasmflow

import com.dylibso.chicory.runtime.Instance

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmInstance internal constructor(
    private val instance: Instance
) {
    actual val memory by lazy { WasmMemory(instance.memory()) }

    actual fun function(name: String) = instance.export(name).toWasmExportFn()
}

@OptIn(ExperimentalWasmApi::class)
internal fun Instance.asWasmInstance() = WasmInstance(this)
