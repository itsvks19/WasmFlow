package com.wasmflow

import com.dylibso.chicory.runtime.HostFunction
import com.dylibso.chicory.runtime.Store
import com.dylibso.chicory.wasm.types.FunctionType

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
actual class WasmStore internal constructor(
    private val store: Store
) {
    actual fun instantiate(
        name: String,
        module: WasmModule
    ): WasmInstance {
        return store.instantiate(name, module.module).asWasmInstance()
    }

    actual fun addFunction(vararg functions: WasmHostFn) = apply {
        for (function in functions) {
            val params = function.signature.params.asValTypes()
            val results = function.signature.results.asValTypes()
            store.addFunction(
                HostFunction(
                    function.moduleName, function.functionName,
                    FunctionType.of(params, results)
                ) { instance, args ->
                    FunctionScope(instance.asWasmInstance()).run {
                        with(function) { implementation(args) }
                    }
                }
            )
        }
    }
}

@ExperimentalWasmApi
actual fun WasmStore(): WasmStore {
    return WasmStore(store = Store())
}
