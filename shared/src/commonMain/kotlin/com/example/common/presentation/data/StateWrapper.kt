package com.example.common.presentation.data

data class StateWrapper<T>(
    val data: T? = null,
    val isLoading: Boolean = false
) {

    companion object {
//		fun <T> loading(default: T): StateWrapper<T> {
//			return StateWrapper(data = default, isLoading = true)
//		}
//
//		fun <T> default(default: T): StateWrapper<T> {
//			return StateWrapper(data = default)
//		}

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
