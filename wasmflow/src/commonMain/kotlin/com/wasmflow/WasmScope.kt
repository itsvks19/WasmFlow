package com.wasmflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@WasmDsl
@ExperimentalWasmApi
class WasmScope {
    val store = WasmStore()
    var defaultModuleName = "wasmflow"

    val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private lateinit var _module: WasmModule

    @OptIn(ExperimentalContracts::class)
    fun module(
        block: WasmModuleScope.() -> WasmModule
    ): WasmScope {
        contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
        _module = block(WasmModuleScope())
        return this
    }

    fun function(
        name: String,
        signature: WasmSignature,
        moduleName: String = defaultModuleName,
        implementation: FunctionScope.(args: LongArray) -> LongArray? = { null }
    ) = apply {
        store.addFunction(
            hostFunction(
                functionName = name,
                signature = signature,
                moduleName = moduleName,
                implementation = implementation
            )
        )
    }

    fun function(
        name: String,
        params: List<WasmType>,
        moduleName: String = defaultModuleName,
        implementation: suspend FunctionScope.(args: LongArray) -> Unit
    ) = apply {
        function(
            name = name,
            signature = signature { params returns void },
            moduleName = moduleName
        ) { args ->
            coroutineScope.launch { implementation(args) }
            null
        }
    }

    fun function(
        name: String,
        results: List<WasmType>,
        moduleName: String = defaultModuleName,
        implementation: FunctionScope.() -> LongArray
    ) = apply {
        function(
            name = name,
            signature = signature { void returns results },
            moduleName = moduleName
        ) { _ -> implementation() }
    }

    fun function(
        name: String,
        params: List<WasmType>,
        results: List<WasmType>,
        moduleName: String = defaultModuleName,
        implementation: FunctionScope.(args: LongArray) -> LongArray
    ) = apply {
        function(
            name = name,
            signature = signature { params returns results },
            moduleName = moduleName
        ) { args -> implementation(args) }
    }

    fun function(
        name: String,
        moduleName: String = defaultModuleName,
        implementation: suspend FunctionScope.() -> Unit
    ) = apply {
        function(
            name = name,
            signature = signature { none },
            moduleName = moduleName
        ) { args ->
            coroutineScope.launch { implementation() }
            null
        }
    }

    @PublishedApi
    internal fun build(): WasmInstance {
        check(::_module.isInitialized) { "WASM module not initialized. Did you forget to call `module { ... }`?" }
        check(defaultModuleName.isNotBlank()) { "Default module name cannot be empty" }
        return store.instantiate(defaultModuleName, _module)
    }
}

@OptIn(ExperimentalContracts::class)
@ExperimentalWasmApi
inline fun wasm(
    block: WasmScope.() -> Unit
): WasmInstance {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return WasmScope().apply(block).build()
}

