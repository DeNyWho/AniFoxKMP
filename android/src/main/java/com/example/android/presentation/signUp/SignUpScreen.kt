package com.example.android.presentation.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android.R
import com.example.android.navigation.Screen
import com.example.android.ui.gray
import com.example.android.ui.lighterGray
import com.example.android.ui.orange
import com.example.android.ui.red
import org.koin.androidx.compose.getViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = getViewModel()
){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        ) {

            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = stringResource(R.string.SignUpWelcome),
                style = MaterialTheme.typography.h3
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

            var name by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                shape = RoundedCornerShape(10.dp),
                value = name,
                onValueChange = { name = it },
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
                singleLine = true,
                label = { Text(stringResource(R.string.signInHintName)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.People,
                        tint = orange,
                        contentDescription = "nameIcon"
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp, top = 15.dp)
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

            var passwordConfirm by remember { mutableStateOf(TextFieldValue("")) }
            var passwordConfirmVisible by remember { mutableStateOf(false) }
            TextField(
                shape = RoundedCornerShape(10.dp),
                value = passwordConfirm,
                onValueChange = { passwordConfirm = it },
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
                visualTransformation = if (passwordConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    val image = if (passwordConfirmVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordConfirmVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordConfirmVisible = !passwordConfirmVisible }) {
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
                    .padding(start = 25.dp, end = 25.dp, top = 75.dp)
                    .height(50.dp),
            ) {
                Text(text = stringResource(R.string.SignUp), fontSize = 20.sp)
            }
        }
        Row(
            modifier = Modifier
                .padding(
                    bottom = 140.dp,
                )
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
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
                text = stringResource(R.string.haveAccount),
                style = MaterialTheme.typography.h4
            )
            Text(
                modifier = Modifier.padding(
                    start = 6.dp,
                    bottom = 8.dp,
                    top = 0.dp,
                    end = 0.dp
                ).clickable {
                    navController.navigate(route = Screen.SignIn.route)
                },
                text = stringResource(R.string.SignIn),
                color = Color.Red,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}