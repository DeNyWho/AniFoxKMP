package com.example.common.domain.common

import com.example.common.core.exception.MyError
import com.example.common.core.wrapper.Event

data class StateMapWrapper<T>(
    val data: Map<T, T> = mapOf(),
    val isLoading: Boolean = false,
    var error: Event<String?> = Event(null)
) {
    companion object {
        fun <T> loading(): StateMapWrapper<T> {
            return StateMapWrapper(isLoading = true)
        }

        fun <T> default(): StateMapWrapper<T> {
            return StateMapWrapper()
        }

        fun <T> error(message: String?): StateMapWrapper<T> {
            return StateMapWrapper(error = Event(message ?: MyError.UNKNOWN_ERROR))
        }

        fun <T> StateMapWrapper<T>.copyKeepData(
            data: StateMapWrapper<T>
        ): StateMapWrapper<T> {
            return this.copy(
                isLoading = data.isLoading
            )
        }
    }
}
