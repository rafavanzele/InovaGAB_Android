package br.com.fiap.inovagab.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.ui.components.InovaTopBar
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    profileName: String,
    homeRoute: String
) {
    val userEmail = authViewModel.currentUser?.email ?: "E-mail não informado"

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            InovaDrawer(
                navController = navController,
                drawerState = drawerState,
                homeRoute = homeRoute,
                profileRoute = when (profileName) {
                    "Operador" -> "operatorProfile"
                    "Gestor" -> "managerProfile"
                    "Liderança" -> "leaderProfile"
                    else -> "operatorProfile"
                },
                notificationsRoute = when (profileName) {
                    "Operador" -> "operatorNotifications"
                    "Gestor" -> "managerNotifications"
                    "Liderança" -> "leaderNotifications"
                    else -> "operatorNotifications"
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Perfil",
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            containerColor = Color(0xFFF5F7FB)
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Perfil do Usuário",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFF1F3F66)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Usuário autenticado",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F3F66)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "E-mail:",
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1F3F66)
                        )

                        Text(
                            text = userEmail,
                            color = Color(0xFF6B7280)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Status:",
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1F3F66)
                        )

                        Text(
                            text = "Autenticado via Firebase Authentication",
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
        }
    }
}