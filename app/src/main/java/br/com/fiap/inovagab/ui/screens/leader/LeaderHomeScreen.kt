package br.com.fiap.inovagab.ui.screens.leader

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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun LeaderHomeScreen(navController: NavController) {

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
                homeRoute = "leaderHome",
                profileRoute = "leaderProfile",
                notificationsRoute = "leaderNotifications"
            )
        }
    ) {

        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Liderança",
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
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .verticalScroll(rememberScrollState()),

                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                HeaderLeader()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Painel da Liderança",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                Text(
                    text = "Acompanhe indicadores estratégicos, resultados e impacto das iniciativas de inovação.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )

                LeaderActionCard(
                    title = "Indicadores estratégicos",
                    description = "Visualize os principais KPIs de inovação.",
                    icon = Icons.Default.Insights,
                    onClick = {
                        navController.navigate("strategicIndicators")
                    }
                )

                LeaderActionCard(
                    title = "Resultados alcançados",
                    description = "Acompanhe ganhos, avanços e entregas.",
                    icon = Icons.Default.EmojiEvents,
                    onClick = {
                        navController.navigate("achievedResults")
                    }
                )

                LeaderActionCard(
                    title = "Engajamento das equipes",
                    description = "Veja a participação dos colaboradores.",
                    icon = Icons.Default.Groups,
                    onClick = {
                        navController.navigate("teamEngagement")
                    }
                )

                LeaderActionCard(
                    title = "Ranking de inovadores",
                    description = "Reconheça colaboradores com mais ideias aprovadas.",
                    icon = Icons.Default.EmojiEvents,
                    onClick = {
                        navController.navigate("contributorRanking")
                    }
                )

                LeaderActionCard(
                    title = "Relatórios executivos",
                    description = "Acesse análises para tomada de decisão.",
                    icon = Icons.Default.BarChart,
                    onClick = {
                        navController.navigate("executiveReports")
                    }
                )

                LeaderActionCard(
                    title = "Orientações estratégicas",
                    description = "Consulte recomendações estratégicas vindas da API.",
                    icon = Icons.Default.Insights,
                    onClick = {
                        navController.navigate("strategicGuidance")
                    }
                )
            }
        }
    }
}

@Composable
fun HeaderLeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.TrendingUp,
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
                text = "Liderança",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )
        }
    }
}

@Composable
fun LeaderActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
fun LeaderHomeScreenPreview() {
    InovaGABTheme {
        LeaderHomeScreen(
            navController = rememberNavController()
        )
    }
}