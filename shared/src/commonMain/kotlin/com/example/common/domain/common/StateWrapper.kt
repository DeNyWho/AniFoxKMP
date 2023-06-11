package com.example.common.domain.common

import com.example.common.core.wrapper.Event

data class StateWrapper<T>(
    var data: T? = null,
    val isLoading: Boolean = false,
    var error: Event<String?> = Event(null)
) {

    companion object {
        fun <T> StateWrapper<T>.isSuccess(): Boolean {
            return error.peekContent() == null && data != null
        }

        fun <T> StateWrapper<T>.copyNewError(
            error: String
        ): StateWrapper<T> {
            return this.copy(
                error = Event(error)
            )
        }

        fun <T> loading(): StateWrapper<T> {
            return StateWrapper(isLoading = true)
        }

        fun <T> default(): StateWrapper<T> {
            return StateWrapper()
        }
        fun <T> StateWrapper<T>.copyKeepData(
            state: StateWrapper<T>
        ): StateWrapper<T> {
            return this.copy(
                isLoading = state.isLoading
            )
        }
    }
}
