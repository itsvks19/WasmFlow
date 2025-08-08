package com.wasmflow

@ExperimentalWasmApi
class HostModuleScope @PublishedApi internal constructor(
    private val moduleName: String,
    private val wasmScope: WasmScope
) {
    fun function(
        name: String,
        signature: WasmSignature,
        implementation: FunctionScope.(args: LongArray) -> LongArray?
    ) = apply {
        with(wasmScope) {
            function(
                name = name,
                signature = signature,
                moduleName = moduleName,
                implementation = implementation
            )
        }
    }

    fun function(
        name: String,
        params: List<WasmType>,
        implementation: suspend FunctionScope.(args: LongArray) -> Unit
    ) = apply {
        with(wasmScope) {
            function(name, params, moduleName, implementation)
        }
    }

    fun function(
        name: String,
        results: List<WasmType>,
        implementation: FunctionScope.() -> LongArray
    ) = apply {
        function(name, signature { returns(results) }) { implementation() }
    }

    fun function(
        name: String,
        implementation: suspend FunctionScope.() -> Unit
    ) = apply {
        with(wasmScope) {
            function(
                name,
                params = emptyList(),
                moduleName = moduleName
            ) { _ -> implementation() }
        }
    }
}
