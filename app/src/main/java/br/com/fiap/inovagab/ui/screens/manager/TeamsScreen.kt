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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
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
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer

data class Team(
    val name: String,
    val members: Int,
    val ideasSubmitted: Int
)

@Composable
fun TeamsScreen(navController: NavController? = null) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val teams = listOf(
        Team(
            name = "Equipe Operacional",
            members = 12,
            ideasSubmitted = 8
        ),
        Team(
            name = "Equipe Estratégica",
            members = 6,
            ideasSubmitted = 5
        ),
        Team(
            name = "Equipe Desenvolvimento",
            members = 9,
            ideasSubmitted = 11
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            InovaDrawer(
                navController = navController,
                drawerState = drawerState,
                homeRoute = "managerHome"
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Equipes",
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            containerColor = Color(0xFFF5F7FB)
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                item {
                    Text(
                        text = "Equipes e colaboradores",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F3F66)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Acompanhe participação e desempenho das equipes.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(teams) { team ->
                    TeamCard(team = team)
                }
            }
        }
    }
}

@Composable
fun TeamCard(team: Team) {

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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Groups,
                    contentDescription = null,
                    tint = Color(0xFF1F3F66),
                    modifier = Modifier.size(30.dp)
                )

                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Colaboradores: ${team.members}",
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Ideias enviadas: ${team.ideasSubmitted}",
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TeamsScreenPreview() {
    InovaGABTheme {
        TeamsScreen()
    }
}

