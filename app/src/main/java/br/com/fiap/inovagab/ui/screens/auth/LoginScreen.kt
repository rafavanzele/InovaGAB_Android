package br.com.fiap.inovagab.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import br.com.fiap.inovagab.ui.theme.LightBackground
import br.com.fiap.inovagab.ui.theme.LightPrimary
import br.com.fiap.inovagab.ui.theme.LightSecondary
import br.com.fiap.inovagab.ui.theme.LightSurface
import br.com.fiap.inovagab.ui.theme.TextSecondary
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: br.com.fiap.inovagab.viewmodel.AuthViewModel
    ) {


    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightBackground
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = "Logo InovaGAB",
                tint = LightPrimary,
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "InovaGAB",
                style = MaterialTheme.typography.headlineLarge,
                color = LightPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Plataforma corporativa de inovação",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Acesse sua conta",
                style = MaterialTheme.typography.titleLarge,
                color = LightPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                label = {
                    Text("E-mail")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            androidx.compose.material3.OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },
                label = {
                    Text("Senha")
                },
                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    authViewModel.login(

                        email = email,
                        password = password,

                        onSuccess = {
                            Toast.makeText(context, "Login realizado com sucesso", Toast.LENGTH_SHORT).show()

                            val route = when (email.lowercase().trim()) {
                                "operador@inovagab.com" -> "operatorHome"
                                "gestor@inovagab.com" -> "managerHome"
                                "lideranca@inovagab.com" -> "leaderHome"
                                else -> null
                            }

                            if (route != null) {
                                navController.navigate(route) {
                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }
                            } else {
                                errorMessage = "Usuário sem perfil definido."
                            }
                        },

                        onError = {
                            Toast.makeText(context, "Erro: $it", Toast.LENGTH_LONG).show()
                            errorMessage = it
                        }
                    )
                },

                enabled = email.isNotBlank() && password.isNotBlank(),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = LightPrimary,
                    disabledContainerColor = Color.LightGray
                )
            ) {

                Text(
                    text = "Entrar no sistema",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ProfileCard(
    title: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                if (isSelected)
                    LightSecondary.copy(alpha = 0.08f)
                else
                    LightSurface
        ),

        border =
            if (isSelected)
                BorderStroke(1.dp, LightSecondary.copy(alpha = 0.5f))
            else
                null,

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(52.dp),

                shape = RoundedCornerShape(16.dp),

                color = LightSecondary.copy(alpha = 0.12f)
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = title,

                    tint = LightSecondary,

                    modifier = Modifier.padding(12.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = LightPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {

    InovaGABTheme {

        //LoginScreen()
    }
}