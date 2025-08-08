package com.wasmflow

@ExperimentalWasmApi
data class WasmHostFn(
    val moduleName: String,
    val functionName: String,
    val signature: WasmSignature,
    val implementation: FunctionScope.(args: LongArray) -> LongArray?
)

@ExperimentalWasmApi
fun hostFunction(
    functionName: String,
    signature: WasmSignature = signature { none },
    moduleName: String = "env",
    implementation: FunctionScope.(args: LongArray) -> LongArray? = { null }
): WasmHostFn {
    return WasmHostFn(
        moduleName = moduleName,
        functionName = functionName,
        signature = signature,
        implementation = implementation
    )
}
