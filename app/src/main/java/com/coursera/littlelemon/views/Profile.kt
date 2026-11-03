package com.coursera.littlelemon.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursera.littlelemon.ToolbarScreen
import com.coursera.littlelemon.users.User

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User? = null,
    onLogout: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ToolbarScreen(
            title = "Profile",
            onBackClick = onBack
        )
    }) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            Box(modifier = Modifier.fillMaxWidth().padding(bottom=32.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "Personal Information",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            val textFieldModifier = Modifier
                .fillMaxWidth().padding(top = 32.dp)

            val textFieldColors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black, unfocusedTextColor = Color.Black, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)

            OutlinedTextField(
                value = user?.firstName.orEmpty(),
                onValueChange = {},
                readOnly = true,
                label = { Text("First Name") },
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors
            )

            OutlinedTextField(
                value = user?.lastName.orEmpty(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Last Name") },
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors
            )


            OutlinedTextField(
                value = user?.email.orEmpty(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Email") },
                modifier = textFieldModifier,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors
            )



            Row(modifier = Modifier.fillMaxWidth().padding(top = 64.dp), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Log out")
                }
            }
        }

    }
}