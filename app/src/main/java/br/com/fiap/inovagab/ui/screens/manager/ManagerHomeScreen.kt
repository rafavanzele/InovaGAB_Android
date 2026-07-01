package br.com.fiap.inovagab.ui.screens.manager

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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import br.com.fiap.inovagab.ui.components.InovaTopBar
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer

@Composable
fun ManagerHomeScreen(navController: NavController? = null) {

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
                homeRoute = "managerHome",
                profileRoute = "managerProfile",
                notificationsRoute = "managerNotifications"
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Gestor",
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
                    .padding(horizontal = 24.dp, vertical = 32.dp),

                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                HeaderManager()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Painel do Gestor",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                Text(
                    text = "Gerencie ideias, acompanhe projetos e avalie propostas enviadas pela equipe.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )

                ManagerActionCard(
                    title = "Ideias pendentes",
                    description = "Avalie propostas aguardando aprovação.",
                    icon = Icons.Default.PendingActions,
                    onClick = {
                        navController?.navigate("pendingIdeas")
                    }
                )

                ManagerActionCard(
                    title = "Projetos em andamento",
                    description = "Gerencie iniciativas em desenvolvimento.",
                    icon = Icons.Default.Assignment,
                    onClick = {
                        navController?.navigate("projects")
                    }
                )

                ManagerActionCard(
                    title = "Aprovações realizadas",
                    description = "Visualize ideias aprovadas recentemente.",
                    icon = Icons.Default.CheckCircle,
                    onClick = {
                        navController?.navigate("approvals")
                    }
                )

                ManagerActionCard(
                    title = "Equipes e colaboradores",
                    description = "Acompanhe participação e desempenho.",
                    icon = Icons.Default.Groups,
                    onClick = {
                        navController?.navigate("teams")
                    }
                )
            }
        }
    }
}

@Composable
fun HeaderManager() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.BusinessCenter,
            contentDescription = null,
            tint = Color(0xFF1F3F66),
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {

            Text(
                text = "Bem-vindo",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Gestor",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )
        }
    }
}

@Composable
fun ManagerActionCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF1F3F66),
                modifier = Modifier.size(42.dp)
            )

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManagerHomeScreenPreview() {

    InovaGABTheme {
        ManagerHomeScreen()
    }
}