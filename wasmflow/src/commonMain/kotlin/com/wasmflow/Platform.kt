package com.wasmflow

sealed interface Platform {
    val name: String

    object Android : Platform {
        override val name: String = "Android"
    }

    object iOS : Platform {
        override val name: String = "iOS"
    }

    object Web : Platform {
        override val name: String = "Web"
    }

    object JVM : Platform {
        override val name: String = "JVM"
    }
}

expect fun getPlatform(): Platform
