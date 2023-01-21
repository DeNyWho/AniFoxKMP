package com.example.android.presentation.signIn

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.android.R
import com.example.android.navigation.Screen
import com.example.android.presentation.splash.SplashViewModel
import com.example.android.ui.gray
import com.example.android.ui.lighterGray
import com.example.android.ui.orange
import com.example.android.ui.red
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SplashViewModel = getViewModel()
){
    Box (
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f),
                painter = painterResource(id = R.drawable.fox_logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.signInFull),
                style = MaterialTheme.typography.h2
            )

            var email by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                shape = RoundedCornerShape(10.dp),
                value = email,
                onValueChange = { email = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lighterGray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedLabelColor = gray
                ),
                textStyle = MaterialTheme.typography.h4,
                singleLine = true,
                label = { Text(stringResource(R.string.signInHintEmail)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        tint = orange,
                        contentDescription = "emailIcon"
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 40.dp)
            )

            var password by remember { mutableStateOf(TextFieldValue("")) }
            var passwordVisible by remember { mutableStateOf(false) }
            TextField(
                shape = RoundedCornerShape(10.dp),
                value = password,
                onValueChange = { password = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = lighterGray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedLabelColor = gray
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                label = { Text(stringResource(R.string.signInHintPassword)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        tint = orange,
                        contentDescription = "passwordIcon"
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 15.dp)
            )

            Button(
                onClick = {

                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .height(50.dp),
            ) {
                Text(text = stringResource(R.string.SignIn), fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .padding(
                        start = 0.dp,
                        top = 90.dp,
                        bottom = 0.dp,
                        end = 0.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            navController.navigate(Screen.Home.route)
                        },
                    text = stringResource(R.string.skip),
                    style = MaterialTheme.typography.h3
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(0.2f)
                        .clickable {
                            navController.navigate(Screen.Home.route)
                        },
                    painter = painterResource(id = R.drawable.skip),
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 0.dp,
                    bottom = 8.dp,
                    top = 0.dp,
                    end = 0.dp
                ),
                text = stringResource(R.string.haveNotAccount),
                style = MaterialTheme.typography.h4
            )
            Text(
                modifier = Modifier.padding(
                    start = 6.dp,
                    bottom = 8.dp,
                    top = 0.dp,
                    end = 0.dp
                ).clickable {
                    navController.navigate(route = Screen.SignUp.route)
                }
                ,
                text = stringResource(R.string.SignUp),
                color = Color.Red,
                style = MaterialTheme.typography.h4,
            )
        }
    }

}