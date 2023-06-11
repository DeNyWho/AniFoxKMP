package com.example.android.presentation.signIn

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.usecases.LoginUseCase
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.auth.TokenResponse
import kotlinx.coroutines.launch

class SignInViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel(){
    private val _login: MutableState<StateListWrapper<TokenResponse>> =
        mutableStateOf(StateListWrapper.default())
    val login: MutableState<StateListWrapper<TokenResponse>> = _login

    fun login(email: String, password: String, onClick: () -> Unit){
        viewModelScope.launch {
            loginUseCase.invoke( email, password).collect { token ->
                _login.value = token
                if(token.data.isNotEmpty()) {
                    onClick.invoke()
                }
            }
        }
    }

}