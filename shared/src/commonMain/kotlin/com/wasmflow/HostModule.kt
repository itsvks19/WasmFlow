package com.wasmflow

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@ExperimentalWasmApi
interface HostModule {
    val name: String

    fun HostModuleScope.functions()
}

@OptIn(ExperimentalContracts::class)
@WasmDsl
@ExperimentalWasmApi
inline fun WasmScope.hostModule(
    name: String,
    block: HostModuleScope.() -> Unit
) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    HostModuleScope(name, this).apply(block)
}
