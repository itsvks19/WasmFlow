package com.wasmflow

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.VALUE_PARAMETER
)
@RequiresOptIn(message = "This API is experimental and may change in the future.")
annotation class ExperimentalWasmApi
