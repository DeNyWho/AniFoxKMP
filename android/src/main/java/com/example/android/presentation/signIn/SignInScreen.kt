package com.example.android.presentation.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.sharp.AlternateEmail
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.android.R
import com.example.android.navigation.Screen
import com.example.android.presentation.splash.SplashViewModel
import com.example.android.ui.MyIcons
import com.example.android.ui.grey
import com.example.android.ui.orange300
import com.example.android.ui.orange400
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = getViewModel()
) {
    Box(
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Start).padding(25.dp, top = 40.dp),
                text = stringResource(R.string.signInFull),
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold,
            )

            var email by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                shape = RoundedCornerShape(10.dp),
                value = email,
                onValueChange = { email = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedLabelColor = grey
                ),
                textStyle = MaterialTheme.typography.h3,
                singleLine = true,
                label = { Text(stringResource(R.string.signInHintEmail), style = MaterialTheme.typography.caption) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Sharp.AlternateEmail,
                        tint = orange300,
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
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedLabelColor = grey
                ),
                textStyle = MaterialTheme.typography.h3,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                label = { Text(stringResource(R.string.signInHintPassword), style = MaterialTheme.typography.caption) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Password,
                        tint = orange300,
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
                    viewModel.login(email.text, password.text)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = orange400,
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
                modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(color = MaterialTheme.colors.primary, thickness = 1.dp, modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp).weight(2f).align(Alignment.CenterVertically),
                    text = stringResource(R.string.typeAuth),
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                )
                Divider(color = MaterialTheme.colors.primary, thickness = 1.dp, modifier = Modifier.weight(1f))
            }

            Button(
                onClick = {

                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = orange400,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .height(50.dp),
            ) {
                Icon(
                    painter = painterResource(MyIcons.Outlined.shikimori),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = stringResource(R.string.shikimori), fontSize = 20.sp)
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 140.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        navController.navigate(Screen.Home.route)
                    },
                text = stringResource(R.string.skip),
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.15f)
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
                },
                text = stringResource(R.string.SignUp),
                color = orange400,
                style = MaterialTheme.typography.h4,
            )
        }
    }

}