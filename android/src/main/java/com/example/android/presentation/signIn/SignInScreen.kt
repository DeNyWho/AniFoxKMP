package com.example.android.presentation.signIn

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.android.R
import com.example.android.presentation.splash.SplashViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SplashViewModel = getViewModel()
){
    signInUI()

}

@Composable
fun signInUI(){
    Box (
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Row (
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.haveNotAccount),
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.SignUp),
                color = Color.Red,
            )
        }
    }
}


@Preview
@Composable
fun previewDaySignIn(){
    signInUI()
}

@Preview( uiMode = UI_MODE_NIGHT_YES)
@Composable
fun previewNightSignIn(){
    signInUI()
}