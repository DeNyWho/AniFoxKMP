package com.example.common.domain.common

import com.example.common.core.exception.MyError
import com.example.common.core.wrapper.Event
import com.example.common.models.common.ContentLightWithPaging

data class StateListWrapper<T>(
    val data: List<T> = listOf(),
    val isLoading: Boolean = false,
    var error: Event<String?> = Event(null)
) {
    companion object {
        fun <T> loading(): StateListWrapper<T> {
            return StateListWrapper(isLoading = true)
        }

        fun <T> default(): StateListWrapper<T> {
            return StateListWrapper()
        }

        fun <T> StateListWrapper<T>.isSuccess(): Boolean {
            return error.peekContent() == null && data != null
        }

        fun <T> StateListWrapper<T>.copyNewError(
            error: String
        ): StateListWrapper<T> {
            return this.copy(
                error = Event(error)
            )
        }

        fun <T> error(message: String?): StateListWrapper<T> {
            return StateListWrapper(error = Event(message ?: MyError.UNKNOWN_ERROR))
        }

        fun <T> StateListWrapper<T>.copyKeepData(
            data: StateListWrapper<T>
        ): StateListWrapper<T> {
            return this.copy(
                isLoading = data.isLoading
            )
        }
    }
}
