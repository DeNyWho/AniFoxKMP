package com.example.android.presentation.signIn

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.common.StateMapWrapper
import com.example.common.usecase.auth.LoginUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SignInViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel(){
    private val _login: MutableState<StateMapWrapper<String>> =
        mutableStateOf(StateMapWrapper.default())
    val login: MutableState<StateMapWrapper<String>> = _login

    fun login(email: String, password: String){
        loginUseCase.invoke( email, password).onEach {
            _login.value = it
        }.launchIn(viewModelScope)
    }


}