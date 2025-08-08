package com.wasmflow

val Long.i32 get() = toInt()
val Long.i64 get() = this
val Long.f32 get() = toFloat()
val Long.f64 get() = toDouble()

@ExperimentalWasmApi
sealed interface WasmType {
    object I32 : WasmType
    object I64 : WasmType
    object F32 : WasmType
    object F64 : WasmType
    object String : WasmType
}
