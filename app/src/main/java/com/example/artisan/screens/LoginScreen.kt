package com.example.artisan.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artisan.R
import com.example.artisan.domain.controller.UserViewModel
import com.example.artisan.ui.theme.inter
import com.example.artisan.HomeScreen

@Composable
fun LoginScreen(navController: NavHostController, userViewModel: UserViewModel) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val topSpacerHeight = screenHeight * 0.25f
    val bottomSpacerHeight = screenHeight * 0.2f

    var email by remember { mutableStateOf("sean@gmail.com") }
    var password by remember { mutableStateOf("123") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(34, 36, 49)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Logo()

            Spacer(modifier = Modifier.height(topSpacerHeight))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                EmailTextField(email) { email = it }
                PasswordTextField(password) { password = it }

                LoginButton(onClick = {
                    userViewModel.loginUser(email, password) { isSuccess ->
                        if (isSuccess) {
                            navController.navigate(
                                HomeScreen(email = email, password = password)
                            )
                        } else {
                            Log.d("LoginScreen", "Invalid Credentials")
                        }
                    }
                })
            }

            Spacer(modifier = Modifier.height(bottomSpacerHeight))

            SignupDirectory()


        }
    }

    val userList by userViewModel.userList.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.getUserList()
    }

}

@Composable
fun ColumnScope.Logo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(R.drawable.artisan_logo_splash).build(),
            contentDescription = "icon",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = "ARTISAN",
            fontSize = 22.sp,
            fontFamily = inter,
            color = Color.White,
            letterSpacing = 15.sp
        )
    }
}


@Composable
fun ColumnScope.EmailTextField(email: String, onValueChange: (String) -> Unit) {

    Column(modifier = Modifier.width(280.dp), verticalArrangement = Arrangement.spacedBy((-5).dp)) {
        TextField(
            value = email,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = "Email icon",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            },
            modifier = Modifier.background(Color.Transparent),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontFamily = inter,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 15.sp
            ),
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White
        )
    }
}

@Composable
fun ColumnScope.PasswordTextField(password: String, onValueChange: (String) -> Unit) {

    Column(modifier = Modifier.width(280.dp), verticalArrangement = Arrangement.spacedBy((-5).dp)) {
        TextField(
            value = password,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Email icon",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            },
            modifier = Modifier.background(Color.Transparent),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontFamily = inter,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 15.sp,
            ),
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White
        )
    }
}


@Composable
fun LoginButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .padding(top = 8.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(34, 36, 49)
            )
        ) {
            Text(text = "LOGIN", fontFamily = inter, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
        }
    }
}



@Composable
fun ColumnScope.SignupDirectory() {
    Text(

        fontFamily = inter,
        color = Color.White,
        fontSize = 15.sp,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White.copy(alpha = 0.6f))) {
                append("First-time User? ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                append("Sign Up")
            }
        },
    )
}

