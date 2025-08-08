# WasmFlow

**WasmFlow** is a Kotlin Multiplatform WebAssembly runtime library that provides a type-safe DSL for creating, managing, and executing WASM modules across Android, Web, and Desktop (JVM) platforms.

## Getting Started

### Prerequisites

- **JDK 21** or higher
- **Android SDK** (for Android targets)

## Usage

### Basic WASM Module Creation

```kotlin
import com.wasmflow.ExperimentalWasmApi
import com.wasmflow.WasmType
import com.wasmflow.signature
import com.wasmflow.wasm
import kotlinx.coroutines.delay

@OptIn(ExperimentalWasmApi::class)
fun main() {
    val instance = wasm {
        // Define the WASM module
        module {
            // Load your WASM module here
            bytes(wasmBytes)
        }

        // Add host functions
        function(
            moduleName = "console",
            name = "log",
            signature = signature { string returns void }
        ) { args ->
            val message = args.string
            println(message)
            null // return value is ignored because this function's return signature is 'void'
        }

        // or do the same thing like this
        function(
            moduleName = "console",
            name = "log",
            params = listOf(WasmType.String)
        ) { args ->
            val message = args.string
            println(message)
        }

        // function with no results are async by default
        function(
            // moduleName = "console", // optional, default is "env"
            name = "hello_async",
            params = listOf(WasmType.I32)
        ) { args ->
            delay(500)
            println("Hello from WASM!")
            println("The value is ${args.i32}")
        }

        // Function that takes no parameters and returns no value
        function("simple_log") {
            println("Hello from WASM!")
        }

        // Function with parameters and return value
        function(
            name = "add_numbers",
            params = listOf(WasmType.I32, WasmType.I32),
            results = listOf(WasmType.I32)
        ) { args ->
            val result = args.i32(0) + args.i32(1) // args[0].toInt() + args[1].toInt()
            longArrayOf(result.toLong())
        }

        // or do the same thing like this
        function(
            name = "add_numbers",
            signature = signature { i32 + i32 returns i32 }
        ) { args ->
            val result = args.i32(0) + args.i32(1)
            longArrayOf(result.toLong())
        }

        // Async function
        function(
            name = "async_operation",
            params = listOf(WasmType.I32)
        ) { args ->
            // Suspend function
            performAsyncOperation(args.i32 /* = args[0].toInt() */)
        }
    }
}

suspend fun performAsyncOperation(value: Int) {
    delay(500)
    println("Async operation completed with value: $value")
}
```

**Note**: This is an experimental project exploring WebAssembly capabilities in Kotlin Multiplatform. APIs may change as the ecosystem evolves.

## Platform Support

**Current Status**: WasmFlow currently supports **Android** and **JVM (Desktop)** platforms.

**Coming Soon**: Kotlin/Wasm support will be available in future releases.
