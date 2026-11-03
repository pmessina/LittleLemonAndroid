package com.coursera.littlelemon.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.coursera.littlelemon.EmailValidation
import com.coursera.littlelemon.User
import com.coursera.littlelemon.validateEmail
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursera.littlelemon.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onRegister: (User) -> Unit = {},
) {

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var submitted by rememberSaveable { mutableStateOf(false) }
    val emailValidation = validateEmail(email)
    val emailError = if (submitted) {
        when (emailValidation) {
            EmailValidation.Empty -> "Email is required"
            EmailValidation.InvalidFormat -> "Enter a valid email address"
            EmailValidation.Valid -> null
        }
    } else null

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Image(
                    painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(width = 200.dp, height = 50.dp)
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Logo"
                )
            }
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(top = 16.dp)
                        .size(width = 150.dp, height = 150.dp)
                        .background(color = Color(0xFF006400), shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "Let's get to know you",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Personal Information",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            val textFieldModifier = Modifier
                .fillMaxWidth()

            OutlinedTextField(
                value = firstName,
                onValueChange = {firstName = it},
                label = { Text("First Name") },
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {lastName = it},
                label = { Text("Last Name") },
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                singleLine = true,
                isError = emailError != null,
                supportingText = emailError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text("Email") },
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        submitted = true
                        if (emailValidation == EmailValidation.Valid) {
                            onRegister(
                                User(
                                    email = email.trim(),
                                    phone = "",
                                    firstName = firstName.trim(),
                                    lastName = lastName.trim(),
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text(text = "Create Account")
                }
            }
        }

    }
}
