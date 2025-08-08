package com.wasmflow

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@ExperimentalWasmApi
data class WasmSignature(
    val params: List<WasmType>,
    val results: List<WasmType>
)

@ExperimentalWasmApi
class WasmSignatureBuilder {
    val void = Unit
    val i32 = WasmType.I32
    val i64 = WasmType.I64
    val f32 = WasmType.F32
    val f64 = WasmType.F64
    val string = WasmType.String

    infix operator fun WasmType.plus(other: WasmType): List<WasmType> = listOf(this, other)
    infix operator fun List<WasmType>.plus(other: WasmType): List<WasmType> = this + listOf(other)

    infix fun WasmType.returns(result: WasmType): WasmSignature {
        return WasmSignature(params = listOf(this), results = listOf(result))
    }

    infix fun List<WasmType>.returns(result: WasmType): WasmSignature {
        return WasmSignature(params = this, results = listOf(result))
    }

    infix fun WasmType.returns(results: List<WasmType>): WasmSignature {
        return WasmSignature(params = listOf(this), results = results)
    }

    infix fun List<WasmType>.returns(results: List<WasmType>): WasmSignature {
        return WasmSignature(params = this, results = results)
    }

    infix fun List<WasmType>.returns(unit: Unit): WasmSignature =
        WasmSignature(params = this, results = emptyList())

    infix fun WasmType.returns(unit: Unit): WasmSignature =
        WasmSignature(params = listOf(this), results = emptyList())

    infix fun returns(result: WasmType): WasmSignature =
        WasmSignature(params = emptyList(), results = listOf(result))

    infix fun returns(result: List<WasmType>): WasmSignature =
        WasmSignature(params = emptyList(), results = result)

    infix fun Unit.returns(result: WasmType): WasmSignature =
        WasmSignature(params = emptyList(), results = listOf(result))

    infix fun Unit.returns(results: List<WasmType>): WasmSignature =
        WasmSignature(params = emptyList(), results = results)

    infix fun Unit.returns(unit: Unit): WasmSignature =
        WasmSignature(params = emptyList(), results = emptyList())

    val none: WasmSignature
        get() = WasmSignature(params = emptyList(), results = emptyList())
}

@OptIn(ExperimentalContracts::class)
@ExperimentalWasmApi
inline fun signature(
    block: WasmSignatureBuilder.() -> WasmSignature
): WasmSignature {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return WasmSignatureBuilder().run(block)
}
