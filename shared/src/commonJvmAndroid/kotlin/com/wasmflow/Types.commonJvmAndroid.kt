package com.wasmflow

import com.dylibso.chicory.wasm.types.ValType

@OptIn(ExperimentalWasmApi::class)
internal fun WasmType.asValTypes() = when (this) {
    WasmType.I32 -> listOf(ValType.I32)
    WasmType.I64 -> listOf(ValType.I64)
    WasmType.F32 -> listOf(ValType.F32)
    WasmType.F64 -> listOf(ValType.F64)
    WasmType.String -> listOf(ValType.I32, ValType.I32)
}

@OptIn(ExperimentalWasmApi::class)
internal fun List<WasmType>.asValTypes() = flatMap { it.asValTypes() }
