package dev.lchang.appue.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.lchang.appue.data.remote.firebase.FirebaseAuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(navController: NavController){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current


    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge)

        //OutlinedTextField for name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            placeholder = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        //OutlinedTextField for email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            placeholder = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        //OutlinedTextField for password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        //OutlinedTextField for confirmPassword
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            placeholder = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        //Button for register
        Button(onClick = {
            if(email.isNotBlank()
                && password.isNotBlank()
                && password == confirmPassword){

                CoroutineScope(context = Dispatchers.Main).launch {
                    val result = FirebaseAuthManager.registerUser(name, email, password)
                    if(result.isSuccess){
                        navController.navigate("login")
                    }else{
                        val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                        //Toast
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()


                    }

                }

                navController.navigate("login")
            }


        }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrar")
        }

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "¿Ya tienes una cuenta? Inicia Sesión")
        }
    }
}