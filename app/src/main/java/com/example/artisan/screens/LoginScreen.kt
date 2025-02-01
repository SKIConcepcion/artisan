package com.example.artisan.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artisan.R
import com.example.artisan.ui.theme.Purple40
import com.example.artisan.ui.theme.inter

@Composable
fun LoginScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(34, 36, 49)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Logo()
            Spacer(modifier = Modifier.height(260.dp))
            Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                EmailTextField()
                PasswordTextField()
            }
            Spacer(modifier = Modifier.height(280.dp))
            SignupDirectory()


        }
    }
}

@Composable
fun ColumnScope.Logo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(R.drawable.artisan_logo).build(),
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
fun ColumnScope.EmailTextField() {
    var email by remember { mutableStateOf("Email") }

    Column(modifier = Modifier.width(280.dp), verticalArrangement = Arrangement.spacedBy((-5).dp)) {
        TextField(
            value = email,
            onValueChange = { input -> email = input },
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
fun ColumnScope.PasswordTextField() {
    var password by remember { mutableStateOf("Password") }

    Column(modifier = Modifier.width(280.dp), verticalArrangement = Arrangement.spacedBy((-5).dp)) {
        TextField(
            value = password,
            onValueChange = { input -> password = input },
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


@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}