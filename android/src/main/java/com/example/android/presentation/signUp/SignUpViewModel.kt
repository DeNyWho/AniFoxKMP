package com.example.android.presentation.signUp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.domain.usecases.RegisterUseCase
import com.example.common.domain.common.StateListWrapper
import com.example.common.models.auth.SignUpRequest
import com.example.common.models.auth.TokenResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val registerUseCase: RegisterUseCase
): ViewModel(){
    private val _register: MutableState<StateListWrapper<TokenResponse>> =
        mutableStateOf(StateListWrapper.default())
    val register: MutableState<StateListWrapper<TokenResponse>> = _register

    fun register(signUpRequest: SignUpRequest,  onClick: () -> Unit){
        viewModelScope.launch {
            registerUseCase.invoke(signUpRequest).collect { token ->
                _register.value = token
                if(token.data.isNotEmpty()) {
                    onClick.invoke()
                }
            }
        }
    }


}