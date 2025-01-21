package com.my.first.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my.first.app.ui.theme.MyFirstAppTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SignUpScreen()
        }
    }
}

@Composable
fun SignUpScreen() {
    MyFirstAppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SignUpCard()
            }
        }
    }
}

@Composable
fun SignUpCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Your Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Join us to get started.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            SignUpForm()
        }
    }
}

@Composable
fun SignUpForm() {
    val context = LocalContext.current
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstnameError by remember { mutableStateOf<String?>(null) }
    var lastnameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var showSnackbar by remember { mutableStateOf(false) }

    fun validateForm(): Boolean {
        var isValid = true
        if (firstname.isBlank()) {
            firstnameError = "First name is required"
            isValid = false
        } else {
            firstnameError = null
        }
        if (lastname.isBlank()) {
            lastnameError = "Last name is required"
            isValid = false
        } else {
            lastnameError = null
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Enter a valid email"
            isValid = false
        } else {
            emailError = null
        }
        if (phone.isBlank() || phone.length < 10) {
            phoneError = "Enter a valid phone number"
            isValid = false
        } else if (phone.matches(Regex("^[+]?[0-9]{10,15}\$"))) {
            phoneError = "Enter a valid phone number"
            isValid = false
        } else {
            phoneError = null
        }
        if (password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordError = null
        }
        if (confirmPassword != password) {
            confirmPasswordError = "Passwords do not match"
            isValid = false
        } else {
            confirmPasswordError = null
        }
        return isValid
    }

    fun formatPhoneNumber(input: String): String {
        val digits = input.filter { it.isDigit() } // Retire tout sauf les chiffres
        return when {
            digits.length <= 3 -> digits
            digits.length <= 6 -> "(${digits.substring(0, 3)}) ${digits.substring(3)}"
            digits.length <= 10 -> "(${digits.substring(0, 3)}) ${
                digits.substring(
                    3,
                    6
                )
            }-${digits.substring(6)}"

            else -> "(${digits.substring(0, 3)}) ${digits.substring(3, 6)}-${
                digits.substring(
                    6,
                    10
                )
            }"
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname = it },
            label = { Text("First Name") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "First Name Icon") },
            modifier = Modifier.fillMaxWidth(),
            isError = firstnameError != null,
            placeholder = { Text("Enter your first name") }
        )
        if (firstnameError != null) {
            Text(text = firstnameError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname = it },
            label = { Text("Last Name") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Last Name Icon") },
            modifier = Modifier.fillMaxWidth(),
            isError = lastnameError != null,
            placeholder = { Text("Enter your last name") }
        )
        if (lastnameError != null) {
            Text(text = lastnameError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            placeholder = { Text("Enter your email") }
        )
        if (emailError != null) {
            Text(text = emailError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
        OutlinedTextField(
            value = phone,
            onValueChange = {
                val formatted = formatPhoneNumber(it)
                if (formatted.length <= 15) phone = formatted
            },
            label = { Text("Phone Number") },
            leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "Phone Icon") },
            modifier = Modifier.fillMaxWidth(),
            isError = phoneError != null,
            placeholder = { Text("(XXX) XXX-XXXX") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
        )

        if (phoneError != null) {
            Text(text = phoneError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            placeholder = { Text("Enter your password") }
        )
        if (passwordError != null) {
            Text(text = passwordError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password Icon") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPasswordError != null,
            placeholder = { Text("Confirm your password") }
        )
        if (confirmPasswordError != null) {
            Text(
                text = confirmPasswordError!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (validateForm()) {
                    firstname = ""
                    lastname = ""
                    email = ""
                    phone = ""
                    password = ""
                    confirmPassword = ""
                    firstnameError = null
                    lastnameError = null
                    emailError = null
                    phoneError = null
                    passwordError = null
                    confirmPasswordError = null
                    showSnackbar = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign Up")
        }
        TextButton(
            onClick = {
                navigateToLoginActivity(context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Sign in")
        }
    }

    AnimatedVisibility(visible = showSnackbar) {
        Snackbar(
            action = {
                TextButton(onClick = { showSnackbar = false }) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Account created successfully")
        }
    }
}

fun navigateToLoginActivity(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun SignScreenPreview() {
    SignUpScreen()
}
