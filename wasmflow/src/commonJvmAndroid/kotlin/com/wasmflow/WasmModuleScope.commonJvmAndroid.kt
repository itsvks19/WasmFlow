package com.wasmflow

import com.dylibso.chicory.wasm.Parser
import kotlinx.io.RawSource
import kotlinx.io.buffered
import kotlinx.io.readByteArray
import java.io.File
import java.io.InputStream
import java.nio.file.Path

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
@ExperimentalWasmApi
@WasmDsl
actual class WasmModuleScope actual constructor() {
    actual fun source(source: RawSource): WasmModule {
        val buffer = source.buffered().peek()
        return WasmModule(Parser.parse(buffer.readByteArray()))
    }

    actual fun bytes(bytes: ByteArray): WasmModule {
        return WasmModule(Parser.parse(bytes))
    }

    fun file(file: File) = WasmModule(Parser.parse(file))
    fun path(path: Path) = WasmModule(Parser.parse(path))
    fun inoutStream(inoutStream: InputStream) = WasmModule(Parser.parse(inoutStream))
}
