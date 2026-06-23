package br.com.fiap.inovagab.ui.screens.leader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
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
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.inovagab.ui.viewmodel.TeamEngagementViewModel

@Composable
fun TeamEngagementScreen(navController: NavController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel: TeamEngagementViewModel = viewModel()
    val teamEngagementData by viewModel.teamEngagementData.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            InovaDrawer(
                navController = navController,
                drawerState = drawerState,
                homeRoute = "leaderHome"
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Engajamento",
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
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                teamEngagementData.forEach { engagement ->

                    TeamEngagementCard(
                        title = engagement.title,
                        value = engagement.value,
                        description = engagement.description,
                        icon = getTeamEngagementIcon(engagement.iconName)
                    )
                }
            }
        }
    }
}

@Composable
fun TeamEngagementCard(
    title: String,
    value: String,
    description: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF2E5AAC)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2E5AAC),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description
                )
            }
        }
    }
}

fun getTeamEngagementIcon(iconName: String): ImageVector {
    return when (iconName) {
        "groups" -> Icons.Default.Groups
        "person_add" -> Icons.Default.PersonAdd
        "team" -> Icons.Default.VolunteerActivism
        "star" -> Icons.Default.Star
        else -> Icons.Default.Groups
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TeamEngagementScreenPreview() {
//    InovaGABTheme {
//        TeamEngagementScreen()
//    }
//}

